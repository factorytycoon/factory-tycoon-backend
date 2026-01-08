package com.factory.tycoon.user.ctrl;

import com.factory.tycoon.user.domain.dto.UserStatusDto;
import com.factory.tycoon.user.domain.entity.UserEntity;
import com.factory.tycoon.user.domain.entity.UserStatus;
import com.factory.tycoon.user.repository.UserStatusRepository;
import com.factory.tycoon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ft/user/status")
@RequiredArgsConstructor
public class UserStatusCtrl {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserStatusDto> createStatus(@RequestBody UserStatusDto dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        UserStatus status = new UserStatus(user, dto.getDate(), dto.getStatus());
        UserStatus saved = userStatusRepository.save(status);
        return ResponseEntity.ok(new UserStatusDto(saved.getId(), user.getUserId(), saved.getDate(), saved.getStatus()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserStatusDto> updateStatus(@PathVariable Long id, @RequestBody UserStatusDto dto) {
        UserStatus status = userStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태입니다."));
        status.setDate(dto.getDate());
        status.setStatus(dto.getStatus());
        userStatusRepository.save(status);
        return ResponseEntity.ok(new UserStatusDto(status.getId(), status.getUser().getUserId(), status.getDate(), status.getStatus()));
    }

    @GetMapping
    public ResponseEntity<List<UserStatusDto>> getStatusByDate(@RequestParam("date") String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        List<UserStatusDto> result = userStatusRepository.findAll().stream()
                .filter(us -> us.getDate().equals(date))
                .map(us -> new UserStatusDto(us.getId(), us.getUser().getUserId(), us.getDate(), us.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
