package com.branches.utils;

import com.branches.model.Client;
import com.branches.model.Vehicle;
import com.branches.model.VehicleType;
import com.branches.request.VehiclePostRequest;
import com.branches.response.ClientGetResponse;
import com.branches.response.ClientVehicleGetResponse;
import com.branches.response.VehicleGetResponse;
import com.branches.response.VehiclePostResponse;

import java.util.ArrayList;
import java.util.List;

public class VehicleUtils {
    public static List<Vehicle> newVehicleList() {
        Client client = ClientUtils.newClientToSave();

        Vehicle vehicle1 = Vehicle.builder().id(1L).vehicleType(VehicleType.carro).brand("Fiat").model("Toro").client(client).build();
        Vehicle vehicle2 = Vehicle.builder().id(2L).vehicleType(VehicleType.moto).brand("Yamaha").model("Mt-07").client(client).build();
        Vehicle vehicle3 = Vehicle.builder().id(3L).vehicleType(VehicleType.moto).brand("Honda").model("Biz").client(client).build();

        return new ArrayList<>(List.of(vehicle1, vehicle2, vehicle3));
    }

    public static List<VehicleGetResponse> newVehicleGetResponseList() {
        ClientVehicleGetResponse clientVehicleGetResponse = ClientUtils.newClientVehicleGetResponse();

        VehicleGetResponse vehicle1 = VehicleGetResponse.builder().vehicleType(VehicleType.carro).brand("Fiat").model("Toro").client(clientVehicleGetResponse).build();
        VehicleGetResponse vehicle2 = VehicleGetResponse.builder().vehicleType(VehicleType.moto).brand("Yamaha").model("Mt-07").client(clientVehicleGetResponse).build();
        VehicleGetResponse vehicle3 = VehicleGetResponse.builder().vehicleType(VehicleType.moto).brand("Honda").model("Biz").client(clientVehicleGetResponse).build();

        return new ArrayList<>(List.of(vehicle1, vehicle2, vehicle3));
    }

    public static Vehicle newVehicleToSave() {
        return Vehicle.builder()
                .id(4L)
                .vehicleType(VehicleType.caminhao)
                .brand("Scania")
                .model("T113")
                .client(ClientUtils.newClientToSave()).build();
    }

    public static VehiclePostRequest newVehiclePostRequest() {
        return VehiclePostRequest.builder()
                .vehicleType(VehicleType.caminhao)
                .brand("Scania")
                .model("T113")
                .clientId(ClientUtils.newClientToSave().getId())
                .build();
    }

    public static VehiclePostResponse newVehiclePostResponse() {
        return VehiclePostResponse.builder()
                .id(4L)
                .vehicleType(VehicleType.caminhao)
                .brand("Scania")
                .model("T113")
                .client(ClientUtils.newClientToSave()).build();
    }
}
