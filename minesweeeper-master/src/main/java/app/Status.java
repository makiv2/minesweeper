package app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Status {

	Bomb(-1), None(0), One(1), Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8);
	public int number;
	Status(int number) {
		this.number = number;
	}

	private static Map map = new HashMap<>();

	static {
		Arrays.stream(Status.values()).forEach(status -> map.put(status.number, status));
	}

	public static Status valueOf(int status) {
		return (Status) map.get(status);
	}

	public int getValue() {
		return number;
	}
}