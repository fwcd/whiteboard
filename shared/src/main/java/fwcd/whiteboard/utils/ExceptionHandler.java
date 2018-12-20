package fwcd.whiteboard.utils;

import java.util.function.Function;

/**
 * Handles exceptions of a specific type.
 * 
 * @param <T> - The exception type to handle
 */
public class ExceptionHandler<T extends Throwable> {
	private final Class<? extends T> exceptionType;
	private final Function<? super T, Boolean> action;
	
	public ExceptionHandler(Class<? extends T> exceptionType, Function<? super T, Boolean> action) {
		this.exceptionType = exceptionType;
		this.action = action;
	}
	
	public Class<? extends T> getExceptionType() {
		return exceptionType;
	}
	
	@SuppressWarnings("unchecked")
	public boolean run(Throwable exception) {
		return action.apply((T) exception);
	}
}
