package be.kdg.keepdishgoing.common.security.api.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for demo
 */
@Getter
@Setter
public class SecuredMessageDto extends UnsecuredMessageDto {

    private String subjectid;
    private String email;
    private String firstName;
    private String lastName;

    private String message;


    private SecuredMessageDto(Builder builder) {
        subjectid = builder.subjectid;
        email = builder.email;
        firstName = builder.firstName;
        lastName = builder.lastName;
        message = builder.message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String subjectid;
        private String email;
        private String firstName;
        private String lastName;
        private String message;

        private Builder() {
        }


        public Builder subjectid(String val) {
            subjectid = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public SecuredMessageDto build() {
            return new SecuredMessageDto(this);
        }
    }
}
