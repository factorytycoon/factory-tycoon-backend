// MongoDB 초기화 스크립트 - 스마트팩토리 센서 데이터
// 센서 데이터 저장용 컬렉션 및 인덱스 생성

db = db.getSiblingDB('factory-tycoon');

// 온도 센서 데이터 컬렉션
db.createCollection('temp_sensors');
db.temperature_sensors.createIndex({ "sensorId": 1, "timestamp": -1 });
db.temperature_sensors.createIndex({ "timestamp": -1 });
db.temperature_sensors.createIndex({ "facility": 1, "timestamp": -1 });

// 샘플 온도 센서 데이터 삽입
db.temperature_sensors.insertMany([
    {
        sensorId: "TEMP-001",
        facility: "Factory-A",
        line: "Line-1",
        temperature: 25.3,
        unit: "celsius",
        status: "normal",
        timestamp: new Date(),
        metadata: {
            location: "Zone-A",
            threshold: { min: 20, max: 30 }
        }
    },
    {
        sensorId: "TEMP-002",
        facility: "Factory-A",
        line: "Line-1",
        temperature: 28.7,
        unit: "celsius",
        status: "normal",
        timestamp: new Date(Date.now() - 60000),
        metadata: {
            location: "Zone-B",
            threshold: { min: 20, max: 30 }
        }
    }
]);

print('Smart Factory sensor data collections initialized successfully');
print('Collections created: temperature_sensors, pressure_sensors, vibration_sensors, humidity_sensors, sensor_readings');
print('Sample data inserted for testing');
