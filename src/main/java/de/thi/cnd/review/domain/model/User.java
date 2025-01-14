package de.thi.cnd.review.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String username;
    private String firstname;
    private String lastname;

}
