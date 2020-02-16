package com.xj.skins.util;


import java.io.*;

/**
 * IO 流工具类
 */
public class Streams {
    protected Streams() {
        throw new UnsupportedOperationException();
    }

    /**
     * 在 {@link #copy(InputStream, OutputStream)} 时默认使用的 buffer 大小
     */
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * 将数据从输入流拷贝的输出流，并关闭两个流
     *
     * @param in  输入流
     * @param out 输出流
     * @return 拷贝的字节数
     * @throws IOException 如果拷贝过程中出现 IO 异常
     */
    public static long copy(InputStream in, OutputStream out) throws IOException {
        return Streams.copy(in, out, Streams.DEFAULT_BUFFER_SIZE);
    }

    /**
     * 将数据从输入流拷贝的输出流，并关闭两个流
     *
     * @param in      输入流
     * @param out     输出流
     * @param bufSize buffer 的大小
     * @return 拷贝的字节数
     * @throws IOException 如果拷贝过程中出现 IO 异常
     */
    public static long copy(InputStream in, OutputStream out, int bufSize) throws IOException {
        return Streams.copy(in, out, true, new byte[bufSize]);
    }

    /**
     * 将数据从输入流拷贝的输出流
     *
     * @param in                输入流
     * @param out               输出流
     * @param closeOutputStream 拷贝完成后是否要关闭输出流
     * @param buf               拷贝时使用的 buffer
     * @return 拷贝的字节数
     * @throws IOException 如果拷贝过程中出现 IO 异常
     */
    public static long copy(InputStream in, OutputStream out, boolean closeOutputStream, byte[] buf) throws IOException {
        try {
            // 总共拷贝的字节数
            long total = 0;
            // 循环拷贝
            while (true) {
                // 本次循环拷贝的字节数
                int res = in.read(buf);
                if (res == -1) {
                    break;
                }
                if (res > 0) {
                    total += res;
                    out.write(buf, 0, res);
                }
            }

            // 返回结果
            return total;
        } finally {
            // 关闭输入流
            Streams.closeQuietly(in);
            // 如果需要则关闭输出流，否则 flush 输出流
            if (closeOutputStream) {
                Streams.closeQuietly(out);
            } else {
                out.flush();
            }
        }
    }

    /**
     * 从输入流中读取所有数据，并关闭输入流，注意，数据长度不能超过 2147483639 字节，不然会出错
     *
     * @param in 输入流
     * @return 读出的数据
     * @throws IOException 如果拷贝过程中出现 IO 异常
     */
    public static byte[] readAllAsBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Streams.copy(in, out);
        return out.toByteArray();
    }

    /**
     * 安静的关闭一个流，忽略任何出现的异常
     *
     * @param closeable 被关闭的流
     */
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }
}