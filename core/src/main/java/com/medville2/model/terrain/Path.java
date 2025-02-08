package com.medville2.model.terrain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.medville2.model.Field;
import com.medville2.model.Terrain;

public class Path {

	private static class PathElement {
		private final Field field;
		private final PathElement prev;

		public PathElement(Field field, PathElement prev) {
			this.field = field;
			this.prev = prev;
		}
	}

	private List<Field> fields;

	private Path(List<Field> fields) {
		this.fields = fields;
	}

	public static Path findPath(Field start, Set<Field> dest, Terrain terrain, Function<Field, Boolean> fieldChecker) {
		List<PathElement> toProcess = new ArrayList<>(50);
		Set<Field> processed = new HashSet<>();

		toProcess.add(new PathElement(start, null));
		// processed.add(start);

		while (!toProcess.isEmpty()) {
			PathElement pe = toProcess.remove(0);
			for (Field n : terrain.getNeighbors(pe.field)) {
				if (!processed.contains(n) && fieldChecker.apply(n)) {
					PathElement pe2 = new PathElement(n, pe);
					toProcess.add(pe2);
					processed.add(n);
					if (dest.contains(n)) {
						List<Field> result = new ArrayList<>();
						while (pe2 != null) {
							result.add(pe2.field);
							pe2 = pe2.prev;
						}
						Collections.reverse(result);
						return new Path(result);
					}
				}
			}
		}
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
