package com.gamekeys.gameshop.mapper;

public interface Mapper<Entity, Dto> {

    Dto convertToDto(Entity entity);
    Entity convertToEntity(Dto dto);
    //BasicDto convertToBasicDto(Entity entity);

}
