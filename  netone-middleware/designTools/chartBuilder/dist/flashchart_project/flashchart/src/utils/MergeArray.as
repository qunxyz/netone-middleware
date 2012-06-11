// ActionScript file
package utils
{

	/**
	 * ...合并多个数组,可合并一维数组和二维数组
	 */
	public class MergeArray
	{
		public function Merge(... args):Array
		{
			return merge1(args);
		}

		private function merge1(arr:Array):Array
		{
			var i:int, j:int;
			var _tempArray:Array=new Array;
			//为了不让每次循环都计算数组长度   
			var len:int=arr.length;
			for (i=0; i < len; i++)
			{
				if (arr[i] is Array)
				{
					var len2:int=arr[i].length;
					for (j=0; j < len2; j++)
					{
						if (arr[i][j] != null)
						{
							_tempArray.push(arr[i][j]);
						}
					}
				}
				else if (arr[i] != null)
				{
					_tempArray.push(arr[i]);
				}
			}
			//查询_tempArray是否还有数组,有则再次转换   
			var m:int;
			var len3:int=_tempArray.length;
			for (i=0; i < len3; i++)
			{
				if (_tempArray[i] is Array)
				{
					return merge1(_tempArray);
					break;
				}
				else
				{
					m++;
					if (m == _tempArray.length)
					{
						return _tempArray;
					}
				}
			}
			return _tempArray;
		}
	}
}
