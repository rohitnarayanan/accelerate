package accelerate.utils;

import static accelerate.utils.CommonConstants.EMPTY_STRING;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import accelerate.utils.bean.DataMap;
import accelerate.utils.exception.AccelerateException;

/**
 * Class providing utility methods for collection operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
 */
public final class CollectionUtil {
	/**
	 * hidden constructor
	 */
	private CollectionUtil() {
	}

	/**
	 * Method converts a {@link Properties} instance to {@link Map} for
	 * non-synchronized traversal. NOTE: In case the properties instance is null a
	 * immutable empty map is return for fail-safe loops.
	 * 
	 * @param aProperties
	 *            - Properties Object
	 * @param type
	 *            - Class to be used for converting value
	 * @param <T>
	 *            - Type of value
	 * @return Map Object
	 */
	public static <T> Map<String, T> convertToMap(Properties aProperties, Class<T> type) {
		Map<String, T> propMap = new HashMap<>();

		if (isEmpty(aProperties)) {
			return propMap;
		}

		aProperties.forEach((key, value) -> propMap.put(Objects.toString(key, EMPTY_STRING), type.cast(value)));

		return propMap;
	}

	/**
	 * @param mapInstanceA
	 * @param mapInstanceB
	 * @return result string
	 */
	public static DataMap compareMaps(Map<String, Object> mapInstanceA, Map<String, Object> mapInstanceB) {
		Object extraA = mapInstanceA.entrySet().stream().filter(entry -> mapInstanceB.get(entry.getKey()) == null)
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));

		Object extraB = mapInstanceB.entrySet().stream().filter(entry -> mapInstanceA.get(entry.getKey()) == null)
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));

		Object conflict = mapInstanceA.entrySet().stream().filter(entry -> mapInstanceB.get(entry.getKey()) != null)
				.map(entry -> new Object[] { entry.getKey(), entry.getValue(), mapInstanceB.get(entry.getKey()) })
				.collect(Collectors.toMap(values -> values[0], values -> new Object[] { values[1], values[2] }));

		return DataMap.buildMap("extraA", extraA, "extraB", extraB, "conflict", conflict);
	}

	/**
	 * @param <E>
	 * @param aCollection
	 * @return first element in the collection
	 */
	public static <E> E firstNotNullElement(Collection<E> aCollection) {
		if (isEmpty(aCollection)) {
			return null;
		}

		for (E element : aCollection) {
			if (element == null) {
				continue;
			}

			return element;
		}

		return null;
	}

	/**
	 * @param <E>
	 * @param aCollection
	 * @return array of elements
	 */
	@SafeVarargs
	public final static <E> List<E> toList(E... aCollection) {
		if (isEmpty(aCollection)) {
			return new ArrayList<>();
		}

		return Arrays.asList(aCollection);
	}

	/**
	 * @param <E>
	 * @param aCollection
	 * @param aArrayType
	 * @return array of elements
	 */
	public static <E> E[] toArray(Collection<? extends E> aCollection, Class<E> aArrayType) {
		if (isEmpty(aCollection)) {
			return null;
		}

		return aCollection.stream().toArray(index -> {
			@SuppressWarnings("unchecked")
			E[] arr = (E[]) Array.newInstance(aArrayType, index);
			return arr;
		});
	}

	/**
	 * Shortcut method to extract a sublist from the middle.
	 *
	 * @param <E>
	 * @param aElementList
	 * @param aStartIndex
	 * @param aFromEndIndex
	 * @return extracted sub list
	 * @throws AccelerateException
	 *             on invalid arguments
	 */
	public static <E> List<E> extractUpto(List<E> aElementList, int aStartIndex, int aFromEndIndex)
			throws AccelerateException {
		if (isEmpty(aElementList)) {
			return Collections.emptyList();
		}

		int length = aElementList.size();
		int start = aStartIndex;
		int end = length - aFromEndIndex;

		if ((start < 0) || (end < 0) || (start >= length) || (end >= length) || (end < start)) {
			throw new AccelerateException("Invalid/Incompatible indexes");
		}

		return aElementList.subList(start, end);
	}

	/**
	 * Shortcut method to extract a sub-array from the middle.
	 *
	 * @param <E>
	 * @param aElementArray
	 * @param aStartIndex
	 * @param aFromEndIndex
	 * @return extracted sub array
	 * @throws AccelerateException
	 *             on invalid arguments
	 */
	public static <E> E[] extractUpto(E[] aElementArray, int aStartIndex, int aFromEndIndex)
			throws AccelerateException {
		if (aElementArray == null) {
			return null;
		}

		if (aElementArray.length == 0) {
			return aElementArray;
		}

		int length = aElementArray.length;
		int start = aStartIndex;
		int end = length - aFromEndIndex;

		if ((start < 0) || (end < 0) || (start >= length) || (end >= length) || (end < start)) {
			throw new AccelerateException("Invalid/Incompatible indexes");
		}

		return ArrayUtils.subarray(aElementArray, start, end);
	}

	/**
	 * @param <E>
	 * @param aList
	 * @return true, if Collection was sorted
	 */
	public static <E extends Comparable<E>> boolean sort(List<E> aList) {
		if (isEmpty(aList)) {
			return false;
		}

		Collections.sort(aList, (aElement1, aElement2) -> aElement1.compareTo(aElement2));
		return true;
	}

	/**
	 * @param <T>
	 * @param aCountMap
	 * @param aKey
	 * @return New Count Value
	 */
	public static <T> int countKey(Map<T, Integer> aCountMap, T aKey) {
		Integer count = aCountMap.get(aKey);
		if (count == null) {
			count = 0;
		}

		int newCount = ++count;
		aCountMap.put(aKey, newCount);
		return newCount;
	}

	/**
	 * @param <P>
	 * @param <Q>
	 * @param <R>
	 * @param aMap
	 * @param aOuterKey
	 * @param aInnerKey
	 * @param aValue
	 * @return {@link Map} to which value was added
	 */
	public static <P, Q, R> Map<Q, R> addToValueMap(Map<P, Map<Q, R>> aMap, P aOuterKey, Q aInnerKey, R aValue) {
		return addToValueMap(aMap, aOuterKey, aInnerKey, aValue, false);
	}

	/**
	 * @param <P>
	 * @param <Q>
	 * @param <R>
	 * @param aMap
	 * @param aOuterKey
	 * @param aInnerKey
	 * @param aValue
	 * @param aOrdered
	 * @return {@link Map} to which value was added
	 */
	public static <P, Q, R> Map<Q, R> addToValueMap(Map<P, Map<Q, R>> aMap, P aOuterKey, Q aInnerKey, R aValue,
			boolean aOrdered) {
		Map<Q, R> valueMap = aMap.get(aOuterKey);
		if ((valueMap == null) || (valueMap == Collections.EMPTY_MAP)) {
			valueMap = aOrdered ? new TreeMap<>() : new HashMap<>();
			aMap.put(aOuterKey, valueMap);
		}

		valueMap.put(aInnerKey, aValue);
		return valueMap;
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param aMap
	 * @param aKey
	 * @param aValue
	 * @return {@link List} to which value was added
	 */
	public static <K, V> List<V> addToValueList(Map<K, List<V>> aMap, K aKey, V aValue) {
		List<V> valueList = aMap.get(aKey);
		if ((valueList == null) || (valueList == Collections.EMPTY_LIST)) {
			valueList = new ArrayList<>();
			aMap.put(aKey, valueList);
		}

		valueList.add(aValue);
		return valueList;
	}
}