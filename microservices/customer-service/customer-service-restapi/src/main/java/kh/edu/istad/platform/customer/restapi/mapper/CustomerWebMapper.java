package kh.edu.istad.platform.customer.restapi.mapper;

import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import kh.edu.istad.platform.customer.restapi.dto.CreateCustomerRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    InitiateCustomerCommand toCommand(CustomerInitiateRequest request);

    CustomerInitiateResponse toResponse(InitiateCustomerResult result);

    @org.mapstruct.Mapping(target = "customerId", source = "id.value")
    @org.mapstruct.Mapping(target = "email", source = "email.value")
    @org.mapstruct.Mapping(target = "phoneNumber", source = "phoneNumber.value")
    CustomerInitiateResponse toResponse(Customer customer);

    Customer toDomain(CreateCustomerRequest request);

    default Email toEmail(String email){
        return new Email(email);
    }

    default PhoneNumber toPhoneNumber(String phoneNumber){
        return new PhoneNumber(phoneNumber);
    }

}
