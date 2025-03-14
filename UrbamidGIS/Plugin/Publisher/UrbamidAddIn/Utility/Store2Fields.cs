using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn
{
    public class Store2Fields
    {
        private ArrayList al1;
        private ArrayList al2;

        public Store2Fields()
        {
            this.al1 = new ArrayList();
            this.al2 = new ArrayList();
        }

        public void Add2Strings(string String1, string String2)
        {
            this.al1.Add((object)String1);
            this.al2.Add((object)String2);
        }

        public string get_GetString1ByIndex(int Index)
        {
            if (this.al1.Count >= Index)
                return this.al1[Index].ToString();
            return "false";
        }

        public string get_GetString2ByIndex(int Index)
        {
            if (this.al2.Count >= Index)
                return this.al2[Index].ToString();
            return "false";
        }

        public string get_GetString1ForString2(string String2)
        {
            int num1 = 0;
            short num2 = checked((short)(this.al2.Count - 1));
            short num3 = (short)num1;
            while ((int)num3 <= (int)num2)
            {
                if (Operators.CompareString(this.al2[(int)num3].ToString(), String2, false) == 0)
                    return this.al1[(int)num3].ToString();
                checked { ++num3; }
            }
            return String.Empty;
        }

        public string get_GetString2ForString1(string String1)
        {
            int num1 = 0;
            short num2 = checked((short)(this.al1.Count - 1));
            short num3 = (short)num1;
            while ((int)num3 <= (int)num2)
            {
                if (Operators.CompareString(this.al1[(int)num3].ToString(), String1, false) == 0)
                    return this.al2[(int)num3].ToString();
                checked { ++num3; }
            }
           
            return String.Empty;
        }

        public ArrayList GetStringlist1
        {
            get
            {
                return this.al1;
            }
        }

        public ArrayList GetStringlist2
        {
            get
            {
                return this.al2;
            }
        }

        public int Count
        {
            get
            {
                return this.al1.Count;
            }
        }

        public bool get_ContainsString1(string String1)
        {
            bool flag1 = false;
            int num1 = 0;
            short num2 = checked((short)(this.al1.Count - 1));
            short num3 = (short)num1;
            while ((int)num3 <= (int)num2)
            {
                if (Operators.ConditionalCompareObjectEqual((object)String1, this.al1[(int)num3], false))
                    return true;
                checked { ++num3; }
            }
            if (!flag1)
                return false;
            return true;
        }

        public bool get_ContainsString2(string String2)
        {
            bool flag1 = false;
            int num1 = 0;
            short num2 = checked((short)(this.al1.Count - 1));
            short num3 = (short)num1;
            while ((int)num3 <= (int)num2)
            {
                if (Operators.ConditionalCompareObjectEqual((object)String2, this.al2[(int)num3], false))
                    return true;
                checked { ++num3; }
            }
            if (!flag1)
                return false;
            return true;
        }
    }
}
