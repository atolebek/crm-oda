package kz.tele2.crmoda.dto.request.role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRoleRequest {

    private String name;

    private String description;

    private String type;

}
