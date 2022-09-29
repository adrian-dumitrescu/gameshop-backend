package com.gamekeys.gameshop.mapper;

public interface Mapper<E, D> {
    D convertToDto(E entity);
    E convertToEntity(D dto);
}
