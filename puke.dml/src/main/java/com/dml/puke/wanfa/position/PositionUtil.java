package com.dml.puke.wanfa.position;

import java.util.HashMap;
import java.util.Map;

public class PositionUtil {
	private static Map<Position, Position> nextPositionAntiClockwiseMap;
	private static Map<Position, Position> nextPositionClockwiseMap;

	static {
		nextPositionAntiClockwiseMap = new HashMap<>();
		nextPositionAntiClockwiseMap.put(Position.dong, Position.bei);
		nextPositionAntiClockwiseMap.put(Position.bei, Position.xi);
		nextPositionAntiClockwiseMap.put(Position.xi, Position.nan);
		nextPositionAntiClockwiseMap.put(Position.nan, Position.dong);

		nextPositionClockwiseMap = new HashMap<>();
		nextPositionClockwiseMap.put(Position.dong, Position.nan);
		nextPositionClockwiseMap.put(Position.nan, Position.xi);
		nextPositionClockwiseMap.put(Position.xi, Position.bei);
		nextPositionClockwiseMap.put(Position.bei, Position.dong);

	}

	public static Position nextPositionClockwise(Position currentPosition) {
		return nextPositionClockwiseMap.get(currentPosition);
	}

	public static Position nextPositionAntiClockwise(Position currentPosition) {
		return nextPositionAntiClockwiseMap.get(currentPosition);
	}

}
