package customstringbuilder;
import sun.misc.FloatingDecimal;
import java.util.Arrays;
public final class custom_string_builder
        implements java.io.Serializable, CharSequence
{
    char value[];
    int count;

    /**
     * Constructs a custom string builder with no characters in it and an
     * initial capacity of 16 characters.
     */
    public custom_string_builder() {
        value = new char[16];
    }

    /**
     * Constructs a custom string builder with no characters in it and an
     * initial capacity specified by the <code>capacity</code> argument.
     *
     * @param      capacity  the initial capacity.
     * @throws     NegativeArraySizeException  if the <code>capacity</code>
     *               argument is less than <code>0</code>.
     */
    public custom_string_builder(int capacity) {
        value = new char[capacity];
    }

    /**
     * Constructs a custom string builder initialized to the contents of the
     * specified string. The initial capacity of the string builder is
     * <code>16</code> plus the length of the string argument.
     *
     * @param   str   the initial contents of the buffer.
     * @throws    NullPointerException if <code>str</code> is <code>null</code>
     */
    public custom_string_builder(String str) {
        value = new char[str.length() + 16];
        append(str);
    }
    
    
    /**
     * Constructs a custom string builder that contains the same characters
     * as the specified <code>CharSequence</code>. The initial capacity of
     * the string builder is <code>16</code> plus the length of the
     * <code>CharSequence</code> argument.
     *
     * @param      seq   the sequence to copy.
     * @throws    NullPointerException if <code>seq</code> is <code>null</code>
     */
    public custom_string_builder(CharSequence seq) {
        this(seq.length() + 16);
        append(seq);
    }

    /**
     * @see     String#valueOf(Object)
     * @see     #append(String)
     */
    public custom_string_builder append(Object obj) {
        return append(String.valueOf(obj));
    }

    void expandCapacity(int minimumCapacity) {
        int newCapacity = (value.length + 1) * 2;
        if (newCapacity < 0) {
            newCapacity = Integer.MAX_VALUE;
        } else if (minimumCapacity > newCapacity) {
            newCapacity = minimumCapacity;
        }
        char[] temp = value;
        value = Arrays.copyOf(value, newCapacity);
        for(int i=0; i<temp.length; i++)
        {
            temp[i]='\0';
        }
    }

    public custom_string_builder append(String str) {
        if (str == null) str = "null";
        int len = str.length();
        if (len == 0) return this;
        int newCount = count + len;
        if (newCount > value.length)
            expandCapacity(newCount);
        str.getChars(0, len, value, count);
        count = newCount;
        return this;
    }

    public void getChars(int srcBegin, int srcEnd, char dst[],
                         int dstBegin)
    {
        if (srcBegin < 0)
            throw new StringIndexOutOfBoundsException(srcBegin);
        if ((srcEnd < 0) || (srcEnd > count))
            throw new StringIndexOutOfBoundsException(srcEnd);
        if (srcBegin > srcEnd)
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    // Appends the specified string builder to this sequence.
    private custom_string_builder append(custom_string_builder sb) {
        if (sb == null)
            return append("null");
        int len = sb.length();
        int newcount = count + len;
        if (newcount > value.length)
            expandCapacity(newcount);
        sb.getChars(0, len, value, count);
        count = newcount;
        return this;
    }

    /**
     * Appends the specified <tt>StringBuffer</tt> to this sequence.
     * <p>
     * The characters of the <tt>StringBuffer</tt> argument are appended,
     * in order, to this sequence, increasing the
     * length of this sequence by the length of the argument.
     * If <tt>sb</tt> is <tt>null</tt>, then the four characters
     * <tt>"null"</tt> are appended to this sequence.
     * <p>
     * Let <i>n</i> be the length of this character sequence just prior to
     * execution of the <tt>append</tt> method. Then the character at index
     * <i>k</i> in the new character sequence is equal to the character at
     * index <i>k</i> in the old character sequence, if <i>k</i> is less than
     * <i>n</i>; otherwise, it is equal to the character at index <i>k-n</i>
     * in the argument <code>sb</code>.
     *
     * @param   sb   the <tt>StringBuffer</tt> to append.
     * @return  a reference to this object.
     */
    public custom_string_builder append(StringBuffer sb) {
        if (sb == null)
            return append("null");
        int len = sb.length();
        int newCount = count + len;
        if (newCount > value.length)
            expandCapacity(newCount);
        sb.getChars(0, len, value, count);
        count = newCount;
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public custom_string_builder append(CharSequence s) {
        if (s == null)
            s = "null";
        if (s instanceof String)
            return this.append((String)s);
        if (s instanceof StringBuffer)
            return this.append((StringBuffer)s);
        if (s instanceof custom_string_builder)
            return this.append((custom_string_builder)s);
        if (s instanceof CompactCharSequence)
            return this.append(((CompactCharSequence) s).toCharArray());
        return this.append(s, 0, s.length());
    }

    /**
     * @throws     IndexOutOfBoundsException {@inheritDoc}
     */
    public custom_string_builder append(CharSequence s, int start, int end) {
        if (s == null)
            s = "null";
        if ((start < 0) || (end < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException(
                    "start " + start + ", end " + end + ", s.length() "
                            + s.length());
        int len = end - start;
        if (len == 0)
            return this;
        int newCount = count + len;
        if (newCount > value.length)
            expandCapacity(newCount);
        for (int i=start; i<end; i++)
            value[count++] = s.charAt(i);
        count = newCount;
        return this;
    }

    public custom_string_builder append(char str[]) {
        int newCount = count + str.length;
        if (newCount > value.length)
            expandCapacity(newCount);
        System.arraycopy(str, 0, value, count, str.length);
        count = newCount;
        return this;
    }

    public custom_string_builder append(char str[], int offset, int len) {
        int newCount = count + len;
        if (newCount > value.length)
            expandCapacity(newCount);
        System.arraycopy(str, offset, value, count, len);
        count = newCount;
        return this;
    }

    /**
     * @see     String#valueOf(boolean)
     * @see     #append(String)
     */
    public custom_string_builder append(boolean b) {
        if (b) {
            int newCount = count + 4;
            if (newCount > value.length)
                expandCapacity(newCount);
            value[count++] = 't';
            value[count++] = 'r';
            value[count++] = 'u';
            value[count++] = 'e';
        } else {
            int newCount = count + 5;
            if (newCount > value.length)
                expandCapacity(newCount);
            value[count++] = 'f';
            value[count++] = 'a';
            value[count++] = 'l';
            value[count++] = 's';
            value[count++] = 'e';
        }
        return this;
    }

    public custom_string_builder append(char c) {
        int newCount = count + 1;
        if (newCount > value.length)
            expandCapacity(newCount);
        value[count++] = c;
        return this;
    }

    /**
     * @see     String#valueOf(int)
     * @see     #append(String)
     */

    final static int [] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE };

    // Requires positive x
    static int stringSize(int x) {
        for (int i=0; ; i++)
            if (x <= sizeTable[i])
                return i+1;
    }

    final static char [] DigitOnes = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    } ;

    final static char [] DigitTens = {
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
            '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
            '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
            '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
            '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
            '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
            '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
            '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
            '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
    } ;

    final static char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    static void getChars(int i, int index, char[] buf) {
        int q, r;
        int charPos = index;
        char sign = 0;

        if (i < 0) {
            sign = '-';
            i = -i;
        }

        // Generate two digits per iteration
        while (i >= 65536) {
            q = i / 100;
            // really: r = i - (q * 100);
            r = i - ((q << 6) + (q << 5) + (q << 2));
            i = q;
            buf [--charPos] = DigitOnes[r];
            buf [--charPos] = DigitTens[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i <= 65536, i);
        for (;;) {
            q = (i * 52429) >>> (16+3);
            r = i - ((q << 3) + (q << 1));  // r = i-(q*10) ...
            buf [--charPos] = digits [r];
            i = q;
            if (i == 0) break;
        }
        if (sign != 0) {
            buf [--charPos] = sign;
        }
    }

    public custom_string_builder append(int i) {
        if (i == Integer.MIN_VALUE) {
            append("-2147483648");
            return this;
        }
        int appendedLength = (i < 0) ? stringSize(-i) + 1
                : stringSize(i);
        int spaceNeeded = count + appendedLength;
        if (spaceNeeded > value.length)
            expandCapacity(spaceNeeded);
        getChars(i, spaceNeeded, value);
        count = spaceNeeded;
        return this;
    }

    /**
     * @see     String#valueOf(long)
     * @see     #append(String)
     */

    static int stringSize(long x) {
        long p = 10;
        for (int i=1; i<19; i++) {
            if (x < p)
                return i;
            p = 10*p;
        }
        return 19;
    }

    static void getChars(long i, int index, char[] buf) {
        long q;
        int r;
        int charPos = index;
        char sign = 0;

        if (i < 0) {
            sign = '-';
            i = -i;
        }

        // Get 2 digits/iteration using longs until quotient fits into an int
        while (i > Integer.MAX_VALUE) {
            q = i / 100;
            // really: r = i - (q * 100);
            r = (int)(i - ((q << 6) + (q << 5) + (q << 2)));
            i = q;
            buf[--charPos] = DigitOnes[r];
            buf[--charPos] = DigitTens[r];
        }

        // Get 2 digits/iteration using ints
        int q2;
        int i2 = (int)i;
        while (i2 >= 65536) {
            q2 = i2 / 100;
            // really: r = i2 - (q * 100);
            r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
            i2 = q2;
            buf[--charPos] = DigitOnes[r];
            buf[--charPos] = DigitTens[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i2 <= 65536, i2);
        for (;;) {
            q2 = (i2 * 52429) >>> (16+3);
            r = i2 - ((q2 << 3) + (q2 << 1));  // r = i2-(q2*10) ...
            buf[--charPos] = digits[r];
            i2 = q2;
            if (i2 == 0) break;
        }
        if (sign != 0) {
            buf[--charPos] = sign;
        }
    }

    public custom_string_builder append(long l) {
        if (l == Long.MIN_VALUE) {
            append("-9223372036854775808");
            return this;
        }
        int appendedLength = (l < 0) ? stringSize(-l) + 1
                : stringSize(l);
        int spaceNeeded = count + appendedLength;
        if (spaceNeeded > value.length)
            expandCapacity(spaceNeeded);
        getChars(l, spaceNeeded, value);
        count = spaceNeeded;
        return this;
    }

    /**
     * @see     String#valueOf(float)
     * @see     #append(String)
     */
    /*public custom_string_builder append(float f) {

        new FloatingDecimal(f).appendTo(this);
        return this;
    }

    /**
     * @see     java.lang.String#valueOf(double)
     * @see     #append(java.lang.String)
     */
    /*public custom_string_builder append(double d) {
        new FloatingDecimal(d).appendTo(this);
        return this;
    }
    */
    /**
     * @since 1.5
     */
    public custom_string_builder appendCodePoint(int codePoint) {
        if (!Character.isValidCodePoint(codePoint)) {
            throw new IllegalArgumentException();
        }
        int n = 1;
        if (codePoint >= Character.MIN_SUPPLEMENTARY_CODE_POINT) {
            n++;
        }
        int newCount = count + n;
        if (newCount > value.length) {
            expandCapacity(newCount);
        }
        if (n == 1) {
            value[count++] = (char) codePoint;
        } else {
            CompactCharSequence.toSurrogates(codePoint, value, count);
            count += n;
        }
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    public custom_string_builder delete(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            end = count;
        if (start > end)
            throw new StringIndexOutOfBoundsException();
        int len = end - start;
        if (len > 0) {
            System.arraycopy(value, start+len, value, start, count-end);
            count -= len;
        }
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    public custom_string_builder deleteCharAt(int index) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        System.arraycopy(value, index+1, value, index, count-index-1);
        count--;
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    public custom_string_builder replace(int start, int end, CompactCharSequence str) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (start > count)
            throw new StringIndexOutOfBoundsException("start > length()");
        if (start > end)
            throw new StringIndexOutOfBoundsException("start > end");

        if (end > count)
            end = count;
        int len = str.length();
        int newCount = count + len - (end - start);
        if (newCount > value.length)
            expandCapacity(newCount);

        System.arraycopy(value, end, value, start + len, count - end);
        str.getChars(value, start);
        count = newCount;
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    public custom_string_builder insert(int index, char str[], int offset,
                                int len)
    {
        if ((index < 0) || (index > length()))
            throw new StringIndexOutOfBoundsException(index);
        if ((offset < 0) || (len < 0) || (offset > str.length - len))
            throw new StringIndexOutOfBoundsException(
                    "offset " + offset + ", len " + len + ", str.length "
                            + str.length);
        int newCount = count + len;
        if (newCount > value.length)
            expandCapacity(newCount);
        System.arraycopy(value, index, value, index + len, count - index);
        System.arraycopy(str, offset, value, index, len);
        count = newCount;
        return this;
    }


    public custom_string_builder insert(int offset, Object obj) {
        return insert(offset, String.valueOf(obj));
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     * @see        #length()
     */
    public custom_string_builder insert(int offset, CompactCharSequence str) {
        if ((offset < 0) || (offset > length()))
            throw new StringIndexOutOfBoundsException(offset);
        if (str == null)
            str = new CompactCharSequence("null");
        int len = str.length();
        int newCount = count + len;
        if (newCount > value.length)
            expandCapacity(newCount);
        System.arraycopy(value, offset, value, offset + len, count - offset);
        str.getChars(value, offset);
        count = newCount;
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    public custom_string_builder insert(int offset, char str[]) {
        if ((offset < 0) || (offset > length()))
            throw new StringIndexOutOfBoundsException(offset);
        int len = str.length;
        int newCount = count + len;
        if (newCount > value.length)
            expandCapacity(newCount);
        System.arraycopy(value, offset, value, offset + len, count - offset);
        System.arraycopy(str, 0, value, offset, len);
        count = newCount;
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public custom_string_builder insert(int dstOffset, CharSequence s) {
        if (s == null)
            s = "null";
        if (s instanceof String)
            return this.insert(dstOffset, (String)s);
        return this.insert(dstOffset, s, 0, s.length());
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public custom_string_builder insert(int dstOffset, CharSequence s,
                                int start, int end)
    {
        if (s == null)
            s = "null";
        if ((dstOffset < 0) || (dstOffset > this.length()))
            throw new IndexOutOfBoundsException("dstOffset "+dstOffset);
        if ((start < 0) || (end < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException(
                    "start " + start + ", end " + end + ", s.length() "
                            + s.length());
        int len = end - start;
        if (len == 0)
            return this;
        int newCount = count + len;
        if (newCount > value.length)
            expandCapacity(newCount);
        System.arraycopy(value, dstOffset, value, dstOffset + len,
                count - dstOffset);
        for (int i=start; i<end; i++)
            value[dstOffset++] = s.charAt(i);
        count = newCount;
        return this;
    }


    public custom_string_builder insert(int offset, boolean b) {
        return insert(offset, String.valueOf(b));
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @see        #length()
     */
    public custom_string_builder insert(int offset, char c) {
        int newCount = count + 1;
        if (newCount > value.length)
            expandCapacity(newCount);
        System.arraycopy(value, offset, value, offset + 1, count - offset);
        value[offset] = c;
        count = newCount;
        return this;
    }


    public custom_string_builder insert(int offset, int i) {
        return insert(offset, String.valueOf(i));
    }


    public custom_string_builder insert(int offset, long l) {
        return insert(offset, String.valueOf(l));
    }

    public custom_string_builder insert(int offset, float f) {
        return insert(offset, String.valueOf(f));
    }


    public custom_string_builder insert(int offset, double d) {
        return insert(offset, String.valueOf(d));
    }

    /**
     * @throws NullPointerException {@inheritDoc}
     */
    public int indexOf(CompactCharSequence str) {
        return indexOf(str, 0);
    }

    /**
     * @throws NullPointerException {@inheritDoc}
     */
    public int indexOf(CompactCharSequence str, int fromIndex) {
        return CompactCharSequence.indexOf(value, 0, count,
                str.toCharArray(), 0, str.length(), fromIndex);
    }

    /**
     * @throws NullPointerException {@inheritDoc}
     */
    public int lastIndexOf(CompactCharSequence str) {
        return lastIndexOf(str, count);
    }

    /**
     * @throws NullPointerException {@inheritDoc}
     */
    public int lastIndexOf(CompactCharSequence str, int fromIndex) {
        return CompactCharSequence.lastIndexOf(value, 0, count,
                str.toCharArray(), 0, str.length(), fromIndex);
    }

    public custom_string_builder reverse() {
        boolean hasSurrogate = false;
        int n = count - 1;
        for (int j = (n-1) >> 1; j >= 0; --j) {
            char temp = value[j];
            char temp2 = value[n - j];
            if (!hasSurrogate) {
                hasSurrogate = (temp >= Character.MIN_SURROGATE && temp <= Character.MAX_SURROGATE)
                        || (temp2 >= Character.MIN_SURROGATE && temp2 <= Character.MAX_SURROGATE);
            }
            value[j] = temp2;
            value[n - j] = temp;
        }
        if (hasSurrogate) {
            // Reverse back all valid surrogate pairs
            for (int i = 0; i < count - 1; i++) {
                char c2 = value[i];
                if (Character.isLowSurrogate(c2)) {
                    char c1 = value[i + 1];
                    if (Character.isHighSurrogate(c1)) {
                        value[i++] = c1;
                        value[i] = c2;
                    }
                }
            }
        }
        return this;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    public String toString() {
        // Create a copy, don't share the array
        return new String(value, 0, count);
    }

    /**
     * Save the state of the <tt>custom_string_builder</tt> instance to a stream
     * (that is, serialize it).
     *
     * @serialData the number of characters currently stored in the string
     *             builder (<tt>int</tt>), followed by the characters in the
     *             string builder (<tt>char[]</tt>).   The length of the
     *             <tt>char</tt> array may be greater than the number of
     *             characters currently stored in the string builder, in which
     *             case extra characters are ignored.
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        s.defaultWriteObject();
        s.writeInt(count);
        s.writeObject(value);
    }

    /**
     * readObject is called to restore the state of the StringBuffer from
     * a stream.
     */
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        count = s.readInt();
        value = (char[]) s.readObject();
    }
    public void setToNull()
    {
        for(int i = 0; i < value.length ; i++)
        {
            value[i]='\0';
        }
    }
}
