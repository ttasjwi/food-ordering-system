# Food-Ordering-System

---

# 사전 준비
- 도커
- 도커 컴포즈


---

# 1. Kafka 초기화

## Zookeeeper 실행
- infrastructure/docker-compose 경로로 이동 후 다음을 실행
- 카프카 실행시 zookeeper를 확인하므로 zookeeper 를 먼저 실행해야 함
```shell
docker-compose -f common.yml -f zookeeper.yml up
echo ruok | nc localhost 2181
```
- imok 응답이 오면 잘 기동된 것


## 카프카 클러스터 실행
infrastructure/docker-compose 경로로 이동 후 다음을 실행
```shell
docker-compose -f common.yml -f kafka_cluster.yml up
```

## 카프카 초기화
```shell
docker-compose -f common.yml -f init_kafka.yml up
```

## 확인
- localhost:9000 접속
- 상단의 Add Cluster 클릭
  - 클러스터명: food-ordering-system-cluster
  - zookeeper 호스트: zookeeper:2181
  - save
- 클러스터 상태를 웹에서 확인 가능

## 종료 후 재실행
- 볼륨 설정을 했으므로, 카프카를 재실행하더라도 상태가 유지됨
- kafka 클러스터 정지 -> zookeeper 정지
- zookeeper 실행 -> kafka 실행
- 상태를 유지하는 것을 확인 가능

---
