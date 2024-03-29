package com.food.ordering.system.support.logging

import com.food.ordering.system.support.logging.impl.DelegatingLogger

fun getLogger(clazz: Class<*>): Logger = DelegatingLogger(clazz)
