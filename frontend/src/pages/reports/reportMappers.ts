export function mapOfficeEmployees(data: any[]): any[] {
  return data.map((employee: any) => ({
    officeEmployeeId: employee.officeEmployeeId,
    email: employee.email,
    firstName: employee.firstName,
    lastName: employee.lastName,
    officeId: employee.office?.officeId ?? 0
  }));
}

export function mapShipments(data: any[]): any[] {
  return data.map((shipment: any) => ({
    shipmentId: shipment.shipmentId,
    senderId: shipment.sender?.clientId ?? 0,
    receiverId: shipment.receiver?.clientId ?? 0,
    registeredById: shipment.registeredBy?.officeEmployeeId ?? '',
    deliveryType: shipment.deliveryType,
    deliveryOfficeId: shipment.deliveryOffice?.officeId ?? '',
    courierEmployeeId: shipment.courierEmployee?.courierEmployeeId ?? '',
    weightGram: shipment.weightGram,
    price: shipment.price,
    status: shipment.status,
    sentDate: shipment.sentDate,
    deliveredDate: shipment.deliveredDate,
    clientPhoneNumber: shipment.clientPhoneNumber
  }));
}
