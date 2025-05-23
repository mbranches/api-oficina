package com.branches.utils;

import com.branches.model.Client;
import com.branches.model.Piece;
import com.branches.model.Repair;
import com.branches.model.Vehicle;
import com.branches.request.RepairEmployeeByRepairPostRequest;
import com.branches.request.RepairPieceByRepairPostRequest;
import com.branches.request.RepairPostRequest;
import com.branches.response.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepairUtils {
    public static List<Repair> newRepairList() {
        Client client = ClientUtils.newClientList().getFirst();
        Vehicle vehicle = VehicleUtils.newVehicleList().getFirst();
        LocalDate date = LocalDate.of(2025, 2, 12);

        Repair repair1 = Repair.builder().id(1L).client(client).vehicle(vehicle).totalValue(1000).endDate(date).build();
        Repair repair2 = Repair.builder().id(2L).client(client).vehicle(vehicle).totalValue(1000).endDate(date).build();
        Repair repair3 = Repair.builder().id(3L).client(client).vehicle(vehicle).totalValue(1000).endDate(date).build();

        return new ArrayList<>(List.of(repair1, repair2, repair3));
    }

    public static List<RepairGetResponse> newRepairGetResponseList() {
        ClientDefaultResponse client = ClientUtils.newClientDefaultResponse();
        VehicleDefaultResponse vehicle = VehicleUtils.newVehicleDefaultResponse();
        LocalDate date = LocalDate.of(2025, 2, 12);

        RepairGetResponse repair1 = RepairGetResponse.builder().id(1L).client(client).vehicle(vehicle).totalValue(1000).endDate(date).build();
        RepairGetResponse repair2 = RepairGetResponse.builder().id(2L).client(client).vehicle(vehicle).totalValue(1000).endDate(date).build();
        RepairGetResponse repair3 = RepairGetResponse.builder().id(3L).client(client).vehicle(vehicle).totalValue(1000).endDate(date).build();

        return new ArrayList<>(List.of(repair1, repair2, repair3));
    }

    public static RepairPostRequest newRepairPostRequest() {
        List<RepairPieceByRepairPostRequest> repairPieces = List.of(RepairPieceUtils.newRepairPiecePostRequest());
        List<RepairEmployeeByRepairPostRequest> repairEmployees = List.of(RepairEmployeeUtils.newRepairEmployeePostRequest());
        LocalDate date = LocalDate.of(2025, 2, 12);

        return RepairPostRequest.builder()
                .clientId(1L)
                .vehicleId(1L)
                .pieces(repairPieces)
                .employees(repairEmployees)
                .endDate(date)
                .build();
    }

    public static Repair newRepairToSave() {
        LocalDate date = LocalDate.of(2025, 2, 12);

        Client client = ClientUtils.newClientList().getFirst();
        Vehicle vehicle = VehicleUtils.newVehicleList().getFirst();
        return Repair.builder()
                .client(client)
                .vehicle(vehicle)
                .endDate(date)
                .build();
    }

    public static Repair newRepairSaved() {
        return newRepairToSave().withId(4L);
    }

    public static RepairPostResponse newRepairPostResponse() {
        LocalDate date = LocalDate.of(2025, 2, 12);

        RepairPieceByRepairResponse repairPieceByRepairPostResponse = RepairPieceUtils.newRepairPieceByRepairPostResponse();
        Piece piece = repairPieceByRepairPostResponse.getPiece();
        piece.setStock(piece.getStock() - repairPieceByRepairPostResponse.getQuantity());

        RepairPostResponse response = RepairPostResponse.builder()
                .id(4L)
                .client(ClientUtils.newClientByRepairPostResponse())
                .vehicle(VehicleUtils.newVehicleDefaultResponse())
                .pieces(List.of(repairPieceByRepairPostResponse))
                .employees(List.of(RepairEmployeeUtils.newRepairEmployeeByRepairPostResponse()))
                .endDate(date)
                .build();

        List<RepairPieceByRepairResponse> pieces = response.getPieces();
        double totalPieces = pieces.stream().mapToDouble(repair -> repair.getPiece().getUnitValue() * repair.getQuantity()).sum();

        List<RepairEmployeeByRepairResponse> employees = response.getEmployees();
        double totalEmployees = employees.stream().mapToDouble(repair -> repair.getEmployee().getCategory().getHourlyPrice() * repair.getHoursWorked()).sum();

        double totalValue = totalPieces + totalEmployees;
        response.setTotalValue(totalValue);

        return response;
    }
}
