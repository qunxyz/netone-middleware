function isDigit()//ֻ����������
{
  return ((event.keyCode >= 48) && (event.keyCode <= 57));
}

function isDigitEvent()//ֻ������"����" "."
{
  return (((event.keyCode >= 48) && (event.keyCode <= 57)) || (event.keyCode == 46) );
}
