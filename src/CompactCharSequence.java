package customstringbuilder;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import static java.lang.Character.MIN_HIGH_SURROGATE;
import static java.lang.Character.MIN_LOW_SURROGATE;
import static java.lang.Character.MIN_SUPPLEMENTARY_CODE_POINT;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hardi
 */
public class CompactCharSequence implements CharSequence, Serializable {
  private final int offset;
  private final int end;
  private final char[] data;

  public CompactCharSequence(String str) {
      data = str.toCharArray();
      offset = 0;
      end = data.length;
    }

  public char charAt(int index) {
    int ix = index+offset;
    if (ix >= end) {
      throw new StringIndexOutOfBoundsException("Invalid index " +
        index + " length " + length());
    }
    return data[ix];
  }

  public int length() {
    return end - offset;
  }
  
  private CompactCharSequence(char[] data, int offset, int end) {
  this.data = data;
  this.offset = offset;
  this.end = end;
}
  public CharSequence subSequence(int start, int end) {
  if (start < 0 || end >= (this.end-offset)) {
    throw new IllegalArgumentException("Illegal range " +
      start + "-" + end + " for sequence of length " + length());
  }
  return new CompactCharSequence(data, start + offset, end + offset);
}
  public String toString() {
  return new String(data, offset, end-offset);
}
  /**
     * Code shared by String and StringBuffer to do searches. The
     * source is the character array being searched, and the target
     * is the string being searched for.
     *
     * @param   source       the characters being searched.
     * @param   sourceOffset offset of the source string.
     * @param   sourceCount  count of the source string.
     * @param   target       the characters being searched for.
     * @param   targetOffset offset of the target string.
     * @param   targetCount  count of the target string.
     * @param   fromIndex    the index to begin searching from.
     */
    static int lastIndexOf(char[] source, int sourceOffset, int sourceCount,
                           char[] target, int targetOffset, int targetCount,
                           int fromIndex)
  {
        /*
         * Check arguments; return immediately where possible. For
         * consistency, don't check for null str.
         */
        int rightIndex = sourceCount - targetCount;
        if (fromIndex < 0) {
            return -1;
        }
        if (fromIndex > rightIndex) {
            fromIndex = rightIndex;
        }
        /* Empty string always matches. */
        if (targetCount == 0) {
            return fromIndex;
        }

        int strLastIndex = targetOffset + targetCount - 1;
        char strLastChar = target[strLastIndex];
        int min = sourceOffset + targetCount - 1;
        int i = min + fromIndex;

    startSearchForLastChar:
        while (true) {
            while (i >= min && source[i] != strLastChar) {
                i--;
            }
            if (i < min) {
                return -1;
            }
            int j = i - 1;
            int start = j - (targetCount - 1);
            int k = strLastIndex - 1;

            while (j > start) {
                if (source[j--] != target[k--]) {
                    i--;
                    continue startSearchForLastChar;
                }
            }
            return start - sourceOffset + 1;
        }
    }
    
    public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
        if (srcBegin < 0) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        }
        if (srcEnd > end) {
            throw new StringIndexOutOfBoundsException(srcEnd);
        }
        if (srcBegin > srcEnd) {
            throw new StringIndexOutOfBoundsException(srcEnd - srcBegin);
        }
        System.arraycopy(data, offset + srcBegin, dst, dstBegin,
             srcEnd - srcBegin);
    }
    
    static int indexOf(char[] source, int sourceOffset, int sourceCount,
                       char[] target, int targetOffset, int targetCount,
                       int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        char first  = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            /* Look for first character. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j] ==
                         target[k]; j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }
    public void setToNull()
    {
        for(int i=0; i<data.length; i++)
        {
            data[i]='\0';
        }
    }
    public char[] toCharArray(){
        return data;
    }
    void getChars(char dst[], int dstBegin) {
        System.arraycopy(data, offset, dst, dstBegin, end);
    }

    static void toSurrogates(int codePoint, char[] dst, int index) {
        int offset = codePoint - MIN_SUPPLEMENTARY_CODE_POINT;
        dst[index+1] = (char)((offset & 0x3ff) + MIN_LOW_SURROGATE);
        dst[index] = (char)((offset >>> 10) + MIN_HIGH_SURROGATE);
    }

}
