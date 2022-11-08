package net.example.finance.mybank.controller;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.mvnprg.openapi.model.AccountDetailResponse;
import com.example.mvnprg.openapi.model.AccountListResponse;
import com.example.mvnprg.openapi.model.CustomerDetailResponse;
import com.example.mvnprg.openapi.model.CustomerListResponse;
import com.example.mvnprg.openapi.model.Notification;

public class ControllerUtil {
    public static CustomerDetailResponse CatchCustomerNotification(Exception ex, CustomerDetailResponse response)
    {
        response.setNotifications(AssemblyNotifications(ex));
        return response;
    }

    public static AccountDetailResponse CatchAccountNotification(Exception ex, AccountDetailResponse response)
    { 
        response.setNotifications(AssemblyNotifications(ex));
        return response;
    }

    public static CustomerListResponse CatchCustomerListNofication(Exception ex, CustomerListResponse response)
    {
        response.setNotifications(AssemblyNotifications(ex));
        return response;
    }

    public static AccountListResponse CatchAccountListNofication(Exception ex, AccountListResponse response)
    {
        response.setNotifications(AssemblyNotifications(ex));
        return response;
    }

    private static List<Notification> AssemblyNotifications(Exception ex)
    {
        Notification notification = new Notification();
        notification.setMessage(ex.getMessage());
        notification.setTimestamp(OffsetDateTime.now());
        notification.setUuid(UUID.randomUUID().toString());
        notification.setSeverity("Error");
        notification.setFieldName("Query");
        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);
        return notifications;
    }
}
