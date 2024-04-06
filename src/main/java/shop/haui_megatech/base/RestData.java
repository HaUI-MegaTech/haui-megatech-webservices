package shop.haui_megatech.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestData<T> {
	private RestStatus status;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String clientMessage;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String devMessage;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public RestData(T data) {
		this.status = RestStatus.SUCCESS;
		this.data = data;
	}
	
	public static RestData<?> error(String clientMessage) {
		return new RestData<>(RestStatus.ERROR, clientMessage, null, null);
	}

	public static RestData<?> error(String clientMessage, String devMessage) {
		return new RestData<>(RestStatus.ERROR, clientMessage, devMessage, null);
	}
}
