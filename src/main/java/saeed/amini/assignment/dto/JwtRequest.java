package saeed.amini.assignment.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	private String username;
	private String password;

}