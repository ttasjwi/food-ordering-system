package com.food.ordering.system.domain.entity

/**
 * 마커 추상 클래스
 */
abstract class AggregateRoot<ID> (id: ID) : BaseEntity<ID>(id)
