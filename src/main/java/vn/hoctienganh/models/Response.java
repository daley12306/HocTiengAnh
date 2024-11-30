package vn.hoctienganh.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
    private String message;
	private int code;
	private Object Data;
}
