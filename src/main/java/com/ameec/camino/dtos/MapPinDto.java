package com.ameec.camino.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapPinDto {
    private Long id;
    private Long trip_id;
    private Double latitude;
    private Double longitude;
    private String city;

    public MapPinDto(MapPinDto mapPinDto) {
        if (mapPinDto.getId() != null) {
            this.id = mapPinDto.getId();
        }
        if (mapPinDto.getTrip_id() != null) {
            this.trip_id = mapPinDto.getTrip_id();
        }
        if (mapPinDto.getLatitude() != null) {
            this.latitude = mapPinDto.getLatitude();
        }
        if (mapPinDto.getLongitude() != null) {
            this.longitude = mapPinDto.getLongitude();
        }
        if (mapPinDto.getCity() != null) {
            this.city = mapPinDto.getCity();
        }
    }
}
