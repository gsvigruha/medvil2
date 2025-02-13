package com.medville2.model.terrain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;

import com.badlogic.gdx.utils.Logger;
import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.building.infra.Road;

public class Path implements Serializable {

	private static final long serialVersionUID = 1L;

	static final Logger LOGGER = new Logger(Path.class.getName(), Logger.INFO);

	private static class PathElement implements Comparable<PathElement>, Serializable {
		private static final long serialVersionUID = 1L;

		private final Field field;
		private final PathElement prev;
		private final float d;
		private final float estimatedRemainingD;

		public PathElement(Field field, PathElement prev, float d, float estimatedRemainingD) {
			this.field = field;
			this.prev = prev;
			this.d = d;
			this.estimatedRemainingD = estimatedRemainingD;
		}

		@Override
		public int compareTo(PathElement o) {
			return Float.compare(this.d + this.estimatedRemainingD, o.d + o.estimatedRemainingD);
		}		
	}

	private List<Field> fields;

	private Path(List<Field> fields) {
		this.fields = fields;
	}

	private static float estimateD(Field start, Field dest) {
		float multiplier = 1f;
		if (start.getObject() != null && start.getObject().getType() == Road.Type) {
			multiplier = 0.5f;
		}
		return multiplier * (Math.abs(start.getI() - dest.getI()) + Math.abs(start.getJ() - dest.getJ()));
	}

	public static Path findPath(Field start, Set<Field> dest, Terrain terrain, Function<Field, Boolean> fieldChecker) {
		PriorityQueue<PathElement> toProcess = new PriorityQueue<>(50);
		Set<Field> processed = new HashSet<>();
		Field destSample = dest.iterator().next();

		toProcess.add(new PathElement(start, null, 0, estimateD(start, destSample)));
		processed.add(start);

		int i = 0;
		while (!toProcess.isEmpty()) {
			PathElement pe = toProcess.poll();
			for (Field n : terrain.getNeighbors(pe.field)) {
				float dN = 1f;
				if (n.getObject() != null &&n.getObject().getType() == Road.Type) {
					dN = 0.5f;
				}
				if (dest.contains(n)) {
					PathElement pe2 = new PathElement(n, pe, pe.d + dN, estimateD(n, destSample));
					List<Field> result = new ArrayList<>();
					while (pe2 != null) {
						result.add(pe2.field);
						pe2 = pe2.prev;
					}

					LOGGER.info("Path of length " + result.size() + " found in " + i + " steps");
					Collections.reverse(result);
					return new Path(result);
				}
				if (!processed.contains(n) && fieldChecker.apply(n)) {
					PathElement pe2 = new PathElement(n, pe, pe.d + dN, estimateD(n, destSample));
					toProcess.add(pe2);
					processed.add(n);
				}
			}
			i++;
		}
		LOGGER.info("No path found in " + i + " steps");
		return null;
	}

	public Field nextField() {
		if (fields.size() > 0) {
			return fields.get(0);
		}
		return null;
	}

	public void consumeField() {
		fields.remove(0);
	}
}
