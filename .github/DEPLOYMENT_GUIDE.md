# Factory Tycoon Backend - CI/CD Deployment Guide

## Architecture Overview

이 프로젝트는 **GitOps 패턴**을 사용합니다:
- **GitHub Actions**: 이미지 빌드 및 AWS ECR 푸시
- **ArgoCD**: ECR 이미지를 EKS 클러스터에 자동 배포

## Requirements

### 1. GitHub Secrets 설정
Repository Settings → Secrets and variables → Actions에서 다음 시크릿 추가:

```
AWS_ACCESS_KEY_ID         # AWS IAM Access Key ID
AWS_SECRET_ACCESS_KEY     # AWS IAM Secret Access Key
AWS_REGION                # AWS Region (예: ap-northeast-2)
GH_PAT                    # GitHub Personal Access Token (factory-tycoon-k8s 저장소 push 권한 필요)
```

### 2. AWS 리소스 준비

#### 필수 AWS 리소스
- **ECR Repository**: `factory-tycoon` 이름으로 생성
- **EKS Cluster**: Kubernetes 클러스터
- **IAM Role**: ECR 푸시 권한 필요

```bash
# ECR 리포지토리 생성 (최초 1회)
aws ecr create-repository --repository-name factory-tycoon --region ap-northeast-2
```

### 3. ArgoCD 설정

#### ArgoCD Application 생성
```yaml
apiVersion: argoproj.io/v1alpha1
kind: Application
metadata:
  name: factory-tycoon-backend
  namespace: argocd
spec:
  project: default
  source:
    repoURL: https://github.com/lgcns5team/factory-tycoon-k8s
    targetRevision: main
    path: backend
  destination:
    server: https://kubernetes.default.svc
    namespace: production
  syncPolicy:
    automated:
      prune: true
      selfHeal: true
      allowEmpty: false
    syncOptions:
    - CreateNamespace=true
```

### 4. Kubernetes 매니페스트 (별도 리포지토리)

#### Deployment
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: factory-tycoon-backend
  namespace: production
spec:
  replicas: 2
  selector:
    matchLabels:
      app: factory-tycoon-backend
  template:
    metadata:
      labels:
        app: factory-tycoon-backend
    spec:
      containers:
      - name: backend
        image: <YOUR_ECR_REGISTRY>/factory-tycoon:latest
        ports:
        - containerPort: 8080
        env:
        - name: MARIADB_URL
          valueFrom:
            secretKeyRef:
              name: factory-tycoon-secrets
              key: mariadb-url
        - name: MARIADB_USERNAME
          valueFrom:
            secretKeyRef:
              name: factory-tycoon-secrets
              key: mariadb-username
        - name: MARIADB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: factory-tycoon-secrets
              key: mariadb-password
        - name: MONGODB_URI
          valueFrom:
            secretKeyRef:
              name: factory-tycoon-secrets
              key: mongodb-uri
        resources:
          requests:
            memory: "512Mi"
            cpu: "500m"
          limits:
            memory: "1Gi"
            cpu: "1000m"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 20
          periodSeconds: 5
```

#### Service
```yaml
apiVersion: v1
kind: Service
metadata:
  name: factory-tycoon-backend
  namespace: production
spec:
  type: ClusterIP
  ports:
  - port: 80
    targetPort: 8080
    protocol: TCP
  selector:
    app: factory-tycoon-backend
```

#### Ingress (ALB)
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: factory-tycoon-backend
  namespace: production
  annotations:
    kubernetes.io/ingress.class: alb
    alb.ingress.kubernetes.io/scheme: internet-facing
    alb.ingress.kubernetes.io/target-type: ip
spec:
  rules:
  - host: api.factory-tycoon.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: factory-tycoon-backend
            port:
              number: 80
```

## 동작 방식 (GitOps)

### CI/CD 파이프라인 흐름
```
코드 Push (deploy 브랜치)
    ↓
GitHub Actions 트리거
    ↓
Docker 이미지 빌드
    ↓
AWS ECR에 이미지 푸시
    ↓
ArgoCD 자동 감지
    ↓
EKS 클러스터에 배포
```

### 트리거 조건
- `deploy` 브랜치에 push
- 다음 파일 변경 감지:
  - `src/**` (소스 코드)
  -프로젝트 구조

```
factory-tycoon-backend/
├── .github/
│   └── workflows/
│       └── deploy-backend.yml    # CI: ECR 이미지 푸시
├── src/                          # Spring Boot 소스
├── Dockerfile                    # 컨테이너 이미지 정의
└── build.gradle                  # Gradle 빌드 설정

factory-tycoon-k8s/              # 별도 리포지토리 (GitOps)
└── backend/
    ├── deployment.yaml          # Kubernetes Deployment
    ├── service.yaml             # Kubernetes Service
    ├── ingress.yaml             # ALB Ingress
    └── secrets.yaml             # Sealed Secrets리케이션 Docker 이미지 빌드
   - ECR에 `latest` 및 `commit SHA` 태그로 푸시
   - 레이어 캐싱으로 빌드 속도 최적화
6. **Image Info**: 빌드된 이미지 정보 출력

### ArgoCD 자동 배포
- ECR의 새 이미지 감지
- Kubernetes 매니페스트와 동기화
- EKS 클러스터에 Rolling Update
- 자동 Health Check 및 Rollback

## 워크플로우 파일

```
.github/workflows/
├── deploy-content.yml
├── deploy-openai.yml
├── deploy-openapi.yml
├── dep 및 확인

### 1. GitHub Actions 확인
```
Repository → Actions → Deploy Factory Tycoon Backend
```
- 워크플로우 실행 상태
- 빌드 로그
- ECR 푸시 결과

### 2. ECR 이미지 확인
```bash
# AWS CLI로 ECR 이미지 확인
aws ecr describe-images \
  --repository-name factory-tycoon \
  --region ap-northeast-2
```

### 3. ArgoCD UI 확인
```
ArgoCD Dashboard → Applications → factory-tycoon-backend
```
- S배포 프로세스

### 개발 → 프로덕션 배포
1. **개발 & 테스트**
   ```bash
   git checkout -b feature/new-feature
   # 개발 진행
   git commit -m "feat: add new feature"
   ```

2. **deploy 브랜치로 머지**
   ```bash
   git checkout deploy
   git merge feature/new-feature
   git push origin deploy
   ```

3. **GitHub Actions 자동 실행**
   - 이미지 빌드 및 ECR 푸시
   - 빌드 완료 알림

4. **ArgoCD 자동 배포**
   - 새 이미지 감지
   - EKS에 Rolling Update
   - Health Check 완료

### 롤백 방법

#### 1. ArgoCD UI에서 롤백
```
ArgoCD Dashboard → History → 이전 버전 선택 → Sync
```

#### 2. kubectl로 롤백
```bash
kubectl rollout undo deployment/factory-tycoon-backend -n production

# 특정 리비전으로 롤백
kubectl rollout undo deployment/factory-tycoon-backend -n production --to-revision=2

# 롤백 상태 확인
kubectl rollout status deployment/factory-tycoon-backend -n production
```

#### 3. ECR 이전 이미지로 복원
```yaml
# k8s manifest에서 이미지 태그 변경
imagECR 푸시 실패
```bash
# ECR 리포지토리 존재 확인
aws ecr describe-repositories --repository-names factory-tycoon --region ap-northeast-2

# IAM 권한 확인
aws sts get-caller-identity

# ECR 로그인 테스트
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin <ECR_REGISTRY>
```

### ArgoCD Sync 실패
```bash
# ArgoCD 애플리케이션 상태 확인
argocd app get factory-tycoon-backend

# 수동 Sync 시도
argocd app sync factory-tycoon-backend

# ArgoCD 로그 확인
kubectl logs -n argocd deployment/argocd-application-controller
```

### Pod 시작 실패
```bash
# Pod 상태 확인
kubectl describe pod <pod-name> -n production

# Pod 로그 확인
kubectl logs <pod-name> -n production

# 이벤트 확인
kubectl get events -n production --sort-by=.metadata.creationTimestamp
```

### 데이터베이스 연결 실패
```bash
# Secret 확인
kubectl get secret factory-tycoon-secrets -n production -o yaml

# 환경 변수 확인
kubectl exec -it <pod-name> -n production -- env | grep MARIADB
```

### 이미지 버전 불일치
```bash
# 현재 실행 중인 이미지 확인
kubectl get pods -n production -o jsonpath="{.items[*].spec.containers[*].image}"

# ECR 최신 이미지 확인
aws ecr describe-images --repository-name factory-tycoon --query 'sort_by(imageDetails,& imagePushedAt)[-1]'
```

## 참고 링크

- [AWS ECR 사용자 가이드](https://docs.aws.amazon.com/ecr/)
- [ArgoCD Documentation](https://argo-cd.readthedocs.io/)
- [EKS Best Practices](https://aws.github.io/aws-eks-best-practices/)
- [Spring Boot on Kubernetes](https://spring.io/guides/gs/spring-boot-kubernetes/)l=mariadb-username='user' \
  --from-literal=mariadb-password='password' \
  --from-literal=mongodb-uri='mongodb://root:pass@mongo-host:27017/factory-tycoon' \
  -n production
```

### IAM 권한 설정
ECR 푸시를 위한 IAM Policy:
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ecr:GetAuthorizationToken",
        "ecr:BatchCheckLayerAvailability",
        "ecr:GetDownloadUrlForLayer",
        "ecr:BatchGetImage",
        "ecr:PutImage",
        "ecr:InitiateLayerUpload",
        "ecr:UploadLayerPart",
        "ecr:CompleteLayerUpload"
      ],
      "Resource": "*"
    }
  ]
}r Hub → ECR)
```yaml
- name: Log in to Amazon ECR
  uses: aws-actions/amazon-ecr-login@v1

- name: Build and push Docker image
  uses: docker/build-push-action@v5
  with:
    context: ./content
    push: true
    tags: |
      ${{ secrets.AWS_ACCOUNT_ID }}.dkr.ecr.${{ secrets.AWS_REGION }}.amazonaws.com/content-service:latest
```

### 환경별 배포 분리
```yaml
on:
  push:
    branches:
      - main      # production
      - dev       # development
      
jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    steps:
      # ... 빌드 단계 ...
      
      - name: Deploy to Production
        if: github.ref == 'refs/heads/main'
        # production 배포 스크립트
        
      - name: Deploy to Development
        if: github.ref == 'refs/heads/dev'
        # development 배포 스크립트
```

## 트러블슈팅

### SSH 연결 실패
- AWS Security Group에서 GitHub Actions IP 허용 확인
- SSH Key 형식 확인 (BEGIN/END 포함)

### 이미지 pull 실패
- Docker Hub 레지스트리 이름 확인
- 이미지 태그 일치 여부 확인

### 서비스 재시작 실패
- docker-compose.yml 서비스 이름 일치 확인
- 컨테이너 이름 중복 확인
