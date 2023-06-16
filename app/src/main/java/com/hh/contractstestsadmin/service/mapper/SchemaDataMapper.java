package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.model.artefacts.Service;
import ru.hh.contract.validator.service.SchemaData;

public class SchemaDataMapper {

  public static SchemaData map(Service service, String schemaBody) {
    schemaBody = "openapi: 3.0.1\n" +
        "info:\n" +
        "  title: mobile-notifier\n" +
        "  version: 2.1.48-SNAPSHOT\n" +
        "paths:\n" +
        "  /device/user/byAvailableDevices:\n" +
        "    get:\n" +
        "      operationId: filterUserIdsByActiveDevicesAvailability\n" +
        "      parameters:\n" +
        "      - name: userId\n" +
        "        in: query\n" +
        "        schema:\n" +
        "          uniqueItems: true\n" +
        "          type: array\n" +
        "          items:\n" +
        "            type: integer\n" +
        "            format: int32\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            application/json:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/UsersInfoDto'\n" +
        "  /device/{deviceId}:\n" +
        "    get:\n" +
        "      operationId: getDevice\n" +
        "      parameters:\n" +
        "      - name: deviceId\n" +
        "        in: path\n" +
        "        required: true\n" +
        "        schema:\n" +
        "          type: integer\n" +
        "          format: int64\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            application/json:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/DeviceDto'\n" +
        "  /device/subscribe:\n" +
        "    post:\n" +
        "      operationId: registerDevice\n" +
        "      requestBody:\n" +
        "        content:\n" +
        "          '*/*':\n" +
        "            schema:\n" +
        "              type: object\n" +
        "              properties:\n" +
        "                userId:\n" +
        "                  type: integer\n" +
        "                  format: int32\n" +
        "                deviceId:\n" +
        "                  type: string\n" +
        "                deviceUuid:\n" +
        "                  type: string\n" +
        "                osType:\n" +
        "                  type: string\n" +
        "                  enum:\n" +
        "                  - MS_WINDOWS_PHONE\n" +
        "                  - I_OS\n" +
        "                  - ANDROID\n" +
        "                  - WEB_BROWSER\n" +
        "                  - JUSTAI\n" +
        "                  - HUAWEI_ANDROID\n" +
        "                  - BOTS\n" +
        "                devMode:\n" +
        "                  type: string\n" +
        "                clientId:\n" +
        "                  type: string\n" +
        "                clientDescription:\n" +
        "                  type: string\n" +
        "                areaId:\n" +
        "                  type: integer\n" +
        "                  format: int32\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            text/xml:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/StatusDto'\n" +
        "  /device/unsubscribe_by_clientid:\n" +
        "    post:\n" +
        "      operationId: unsubscribeByClientId\n" +
        "      requestBody:\n" +
        "        content:\n" +
        "          '*/*':\n" +
        "            schema:\n" +
        "              type: object\n" +
        "              properties:\n" +
        "                userId:\n" +
        "                  type: integer\n" +
        "                  format: int32\n" +
        "                clientId:\n" +
        "                  type: string\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            text/xml:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/StatusDto'\n" +
        "  /device/unsubscribe_by_uuid:\n" +
        "    put:\n" +
        "      operationId: unsubscribeByUuid\n" +
        "      requestBody:\n" +
        "        content:\n" +
        "          '*/*':\n" +
        "            schema:\n" +
        "              type: object\n" +
        "              properties:\n" +
        "                userId:\n" +
        "                  type: integer\n" +
        "                  format: int32\n" +
        "                deviceUuid:\n" +
        "                  type: string\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            application/json:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/StatusDto'\n" +
        "  /device/unsubscribe:\n" +
        "    post:\n" +
        "      operationId: unsubscribeUser\n" +
        "      requestBody:\n" +
        "        content:\n" +
        "          '*/*':\n" +
        "            schema:\n" +
        "              type: object\n" +
        "              properties:\n" +
        "                userId:\n" +
        "                  type: integer\n" +
        "                  format: int32\n" +
        "                deviceId:\n" +
        "                  type: string\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            text/xml:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/StatusDto'\n" +
        "  /fixture/device_notification:\n" +
        "    post:\n" +
        "      operationId: createDeviceNotification\n" +
        "      requestBody:\n" +
        "        content:\n" +
        "          '*/*':\n" +
        "            schema:\n" +
        "              type: object\n" +
        "              properties:\n" +
        "                deviceToken:\n" +
        "                  type: string\n" +
        "                title:\n" +
        "                  type: string\n" +
        "                text:\n" +
        "                  type: string\n" +
        "                linkUrl:\n" +
        "                  type: string\n" +
        "                linkMode:\n" +
        "                  type: string\n" +
        "                  enum:\n" +
        "                  - EXTERNAL\n" +
        "                  - INTERNAL\n" +
        "                  - DEEP\n" +
        "                notificationUuid:\n" +
        "                  type: string\n" +
        "                messageType:\n" +
        "                  type: string\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            '*/*': {}\n" +
        "  /fixture/user_notification:\n" +
        "    post:\n" +
        "      operationId: createUserNotification\n" +
        "      requestBody:\n" +
        "        content:\n" +
        "          '*/*':\n" +
        "            schema:\n" +
        "              type: object\n" +
        "              properties:\n" +
        "                userId:\n" +
        "                  type: integer\n" +
        "                  format: int32\n" +
        "                title:\n" +
        "                  type: string\n" +
        "                text:\n" +
        "                  type: string\n" +
        "                linkUrl:\n" +
        "                  type: string\n" +
        "                linkMode:\n" +
        "                  type: string\n" +
        "                  enum:\n" +
        "                  - EXTERNAL\n" +
        "                  - INTERNAL\n" +
        "                  - DEEP\n" +
        "                notificationUuid:\n" +
        "                  type: string\n" +
        "                messageType:\n" +
        "                  type: string\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            '*/*': {}\n" +
        "  /fixture/device:\n" +
        "    get:\n" +
        "      operationId: getDevice_1\n" +
        "      parameters:\n" +
        "      - name: deviceToken\n" +
        "        in: query\n" +
        "        schema:\n" +
        "          type: string\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            application/json:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/DeviceDto'\n" +
        "  /notification/{notificationUuid}:\n" +
        "    delete:\n" +
        "      operationId: deleteNotification\n" +
        "      parameters:\n" +
        "      - name: notificationUuid\n" +
        "        in: path\n" +
        "        required: true\n" +
        "        schema:\n" +
        "          type: string\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            application/json:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/StatusDto'\n" +
        "  /notification:\n" +
        "    get:\n" +
        "      operationId: getNotifications\n" +
        "      parameters:\n" +
        "      - name: userId\n" +
        "        in: query\n" +
        "        schema:\n" +
        "          type: integer\n" +
        "          format: int32\n" +
        "      - name: uuid\n" +
        "        in: query\n" +
        "        schema:\n" +
        "          type: string\n" +
        "      - name: clientId\n" +
        "        in: query\n" +
        "        schema:\n" +
        "          type: string\n" +
        "      - name: limit\n" +
        "        in: query\n" +
        "        schema:\n" +
        "          type: integer\n" +
        "          format: int32\n" +
        "          default: 20\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            application/json:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/PushNotificationsListDto'\n" +
        "  /notification/count:\n" +
        "    get:\n" +
        "      operationId: getNotificationsCount\n" +
        "      parameters:\n" +
        "      - name: userId\n" +
        "        in: query\n" +
        "        schema:\n" +
        "          type: integer\n" +
        "          format: int32\n" +
        "      - name: uuid\n" +
        "        in: query\n" +
        "        schema:\n" +
        "          type: string\n" +
        "      - name: clientId\n" +
        "        in: query\n" +
        "        schema:\n" +
        "          type: string\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            application/json:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/PushNotificationsCountDto'\n" +
        "  /notification/markAllSeen:\n" +
        "    put:\n" +
        "      operationId: markAllSeen\n" +
        "      requestBody:\n" +
        "        content:\n" +
        "          '*/*':\n" +
        "            schema:\n" +
        "              type: object\n" +
        "              properties:\n" +
        "                userId:\n" +
        "                  type: integer\n" +
        "                  format: int32\n" +
        "                uuid:\n" +
        "                  type: string\n" +
        "                clientId:\n" +
        "                  type: string\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            application/json:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/StatusDto'\n" +
        "  /notification/{notificationUuid}/markSeen:\n" +
        "    put:\n" +
        "      operationId: markNotificationSeen\n" +
        "      parameters:\n" +
        "      - name: notificationUuid\n" +
        "        in: path\n" +
        "        required: true\n" +
        "        schema:\n" +
        "          type: string\n" +
        "      responses:\n" +
        "        default:\n" +
        "          description: default response\n" +
        "          content:\n" +
        "            application/json:\n" +
        "              schema:\n" +
        "                $ref: '#/components/schemas/StatusDto'\n" +
        "components:\n" +
        "  schemas:\n" +
        "    UsersInfoDto:\n" +
        "      required:\n" +
        "      - userIds\n" +
        "      type: object\n" +
        "      properties:\n" +
        "        userIds:\n" +
        "          uniqueItems: true\n" +
        "          type: array\n" +
        "          items:\n" +
        "            type: integer\n" +
        "            format: int32\n" +
        "    DeviceDto:\n" +
        "      required:\n" +
        "      - deviceId\n" +
        "      - deviceOsType\n" +
        "      - deviceToken\n" +
        "      type: object\n" +
        "      properties:\n" +
        "        deviceId:\n" +
        "          type: integer\n" +
        "          format: int64\n" +
        "          xml:\n" +
        "            name: device-id\n" +
        "        deviceToken:\n" +
        "          type: string\n" +
        "          xml:\n" +
        "            name: device-token\n" +
        "        registrationTime:\n" +
        "          type: integer\n" +
        "          format: int64\n" +
        "          nullable: true\n" +
        "          xml:\n" +
        "            name: registration-time\n" +
        "        lastVisitTime:\n" +
        "          type: integer\n" +
        "          format: int64\n" +
        "          nullable: true\n" +
        "          xml:\n" +
        "            name: last-visit-time\n" +
        "        active:\n" +
        "          type: boolean\n" +
        "          nullable: true\n" +
        "        deviceOsType:\n" +
        "          type: string\n" +
        "          xml:\n" +
        "            name: os-type\n" +
        "        deviceMode:\n" +
        "          type: string\n" +
        "          nullable: true\n" +
        "          xml:\n" +
        "            name: device-mode\n" +
        "        ownerUserId:\n" +
        "          type: integer\n" +
        "          format: int32\n" +
        "          nullable: true\n" +
        "          xml:\n" +
        "            name: owner-user-id\n" +
        "        atLeastOnceUsedByRegisteredUser:\n" +
        "          type: boolean\n" +
        "          nullable: true\n" +
        "          xml:\n" +
        "            name: at-least-once-used-by-registered-user\n" +
        "        nested:\n" +
        "          $ref: '#/components/schemas/NestedDto'\n" +
        "        uuid:\n" +
        "          type: string\n" +
        "          nullable: true\n" +
        "      xml:\n" +
        "        name: device\n" +
        "    NestedDto:\n" +
        "      required:\n" +
        "      - requiredField\n" +
        "      type: object\n" +
        "      properties:\n" +
        "        nullableField:\n" +
        "          type: string\n" +
        "          nullable: true\n" +
        "        requiredField:\n" +
        "          type: string\n" +
        "    StatusDto:\n" +
        "      type: object\n" +
        "      properties:\n" +
        "        content:\n" +
        "          type: string\n" +
        "          nullable: true\n" +
        "      xml:\n" +
        "        name: status\n" +
        "    PushNotificationDto:\n" +
        "      required:\n" +
        "      - linkMode\n" +
        "      - linkUrl\n" +
        "      - notificationUuid\n" +
        "      - seenByUser\n" +
        "      - text\n" +
        "      type: object\n" +
        "      properties:\n" +
        "        notificationUuid:\n" +
        "          type: string\n" +
        "        title:\n" +
        "          type: string\n" +
        "          nullable: true\n" +
        "        text:\n" +
        "          type: string\n" +
        "        seenByUser:\n" +
        "          type: boolean\n" +
        "        linkUrl:\n" +
        "          type: string\n" +
        "        linkMode:\n" +
        "          type: string\n" +
        "          enum:\n" +
        "          - EXTERNAL\n" +
        "          - INTERNAL\n" +
        "          - DEEP\n" +
        "        messageType:\n" +
        "          type: string\n" +
        "          nullable: true\n" +
        "    PushNotificationsListDto:\n" +
        "      required:\n" +
        "      - notifications\n" +
        "      type: object\n" +
        "      properties:\n" +
        "        notifications:\n" +
        "          type: array\n" +
        "          items:\n" +
        "            $ref: '#/components/schemas/PushNotificationDto'\n" +
        "    PushNotificationsCountDto:\n" +
        "      required:\n" +
        "      - notificationsCount\n" +
        "      type: object\n" +
        "      properties:\n" +
        "        notificationsCount:\n" +
        "          type: integer\n" +
        "          format: int64";
    return new SchemaData(schemaBody, service.getVersion());
  }
}
