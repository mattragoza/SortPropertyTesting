import static org.junit.Assert.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

public class SortProperties
{
	static int num_arrays = 1000;
	static int max_array_len = 10000;
	static List<int[]> arrays;
	
	@Before
	public void setUp()
	{
		Random rand = new Random();
		arrays = new ArrayList<int[]>(num_arrays);
		for (int i=0; i<num_arrays; i++)
		{
			int array_len = rand.nextInt(max_array_len);
			int[] array = new int[array_len];
			for (int j=0; j<array_len; j++)
				array[j] = rand.nextInt();
			arrays.add(array);
		}
	}
	
	@Test
	public void sameLength()
	{
		for (int[] array : arrays)
		{
			int len_before = array.length;
			Arrays.sort(array);
			int len_after = array.length;
			assertEquals(len_before, len_after);
		}
	}
	
	@Test
	public void nonDecreasing()
	{
		for (int[] array : arrays)
		{
			Arrays.sort(array);
			
			if (array.length > 1)
			{
				int prev = array[0];
				for (int i=1; i<array.length; i++)
				{
					int curr = array[i];
					assertFalse(curr < prev);
					prev = curr;
				}
			}
		}
	}
	
	@Test
	public void equalOrIncreasing()
	{
		for (int[] array : arrays)
		{
			Arrays.sort(array);
			
			if (array.length > 1)
			{
				int prev = array[0];
				for (int i=1; i<array.length; i++)
				{
					int curr = array[i];
					assertTrue(curr >= prev);
					prev = curr;
				}
			}
		}
	}
	
	@Test
	public void allElementsPresent()
	{
		for (int[] array : arrays)
		{
			Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
			for (int i : array)
			{
				if (counts.containsKey(i))
					counts.put(i, counts.get(i) + 1);
				else
					counts.put(i, 1);
			}
			
			Arrays.sort(array);
			
			for (int o : array)
			{
				if (counts.containsKey(o))
				{
					if (counts.get(o) > 1)
						counts.put(o, counts.get(o) - 1);
					else
						counts.remove(o);
				}
			}
			assertTrue(counts.isEmpty());
		}
	}

	@Test
	public void noExtraElements()
	{
		for (int[] array : arrays)
		{
			Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
			for (int i : array)
			{
				if (counts.containsKey(i))
					counts.put(i, counts.get(i) + 1);
				else
					counts.put(i, 1);
			}
			
			Arrays.sort(array);
			
			for (int i : array)
			{
				if (counts.containsKey(i) && counts.get(i) > 0)
					counts.put(i, counts.get(i) - 1);
				else
					fail();
			}
		}
	}

	@Test
	public void testDeterministic()
	{
		for (int[] array : arrays)
		{
			int[] array2 = Arrays.copyOf(array, array.length);
			Arrays.sort(array);
			Arrays.sort(array2);
			assertTrue(Arrays.equals(array, array2));
		}
	}
	
	@Test
	public void testIdempotent()
	{
		for (int[] array : arrays)
		{
			Arrays.sort(array);
			int[] array2 = Arrays.copyOf(array, array.length);
			Arrays.sort(array);
			assertTrue(Arrays.equals(array, array2));
		}
	}
}

