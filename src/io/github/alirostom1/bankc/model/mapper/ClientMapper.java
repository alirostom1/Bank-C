package io.github.alirostom1.bankc.model.mapper;

import io.github.alirostom1.bankc.model.dto.ClientRequestDto;
import io.github.alirostom1.bankc.model.dto.ClientResponseDto;
import io.github.alirostom1.bankc.model.entity.Client;

public class ClientMapper {
    public final static Client dtoToClient(ClientRequestDto dto){
        return new Client(dto.id(),dto.name(),dto.email());
    }
    public final static ClientResponseDto clientToDto(Client client){
        return new ClientResponseDto(client.id(),client.name(),client.email());
    }
}
