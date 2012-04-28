function isDigit()//只能输入数字
{
  return ((event.keyCode >= 48) && (event.keyCode <= 57));
}

function isDigitEvent()//只能输入"数字" "."
{
  return (((event.keyCode >= 48) && (event.keyCode <= 57)) || (event.keyCode == 46) );
}
