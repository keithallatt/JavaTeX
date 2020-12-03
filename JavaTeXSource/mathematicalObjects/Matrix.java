package mathematicalObjects;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

final public class Matrix {
	private final int M; // number of rows
	private final int N; // number of columns
	private final double[][] data; // M-by-N array

	// create M-by-N matrix of 0's
	public Matrix(int M, int N) {
		this.M = M;
		this.N = N;
		data = new double[M][N];
	}

	// create matrix based on 2d array
	public Matrix(double[][] data) {
		M = data.length;
		N = data[0].length;
		this.data = new double[M][N];
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				this.data[i][j] = data[i][j];
	}

	// copy constructor
	private Matrix(Matrix A) {
		this(A.data);
	}

	// from string
	public Matrix(String m) {
		this(parseString(m));
	}

	public Matrix(Double[][] data) {
		M = data.length;
		N = data[0].length;
		this.data = new double[M][N];
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				this.data[i][j] = data[i][j];
	}

	// create and return a random M-by-N matrix with values between 0 and 1
	public static Matrix random(int M, int N) {
		Matrix A = new Matrix(M, N);
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				A.data[i][j] = Math.random();
		return A;
	}

	// create and return the N-by-N identity matrix
	public static Matrix identity(int N) {
		Matrix I = new Matrix(N, N);
		for (int i = 0; i < N; i++)
			I.data[i][i] = 1;
		return I;
	}

	// swap rows i and j
	private void swap(int i, int j) {
		double[] temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	// create and return the transpose of the invoking matrix
	public Matrix transpose() {
		Matrix A = new Matrix(N, M);
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				A.data[j][i] = this.data[i][j];
		return A;
	}

	// return C = A + B
	public Matrix add(Matrix B) {
		Matrix A = this;
		if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
		Matrix C = new Matrix(M, N);
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				C.data[i][j] = A.data[i][j] + B.data[i][j];
		return C;
	}

	// return C = A - B
	public Matrix subtract(Matrix B) {
		Matrix A = this;
		if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
		Matrix C = new Matrix(M, N);
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				C.data[i][j] = A.data[i][j] - B.data[i][j];
		return C;
	}

	// does A = B exactly?
	public boolean eq(Matrix B) {
		Matrix A = this;
		if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				if (A.data[i][j] != B.data[i][j]) return false;
		return true;
	}

	// return C = A * B
	public Matrix times(Matrix B) {
		Matrix A = this;
		if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
		Matrix C = new Matrix(A.M, B.N);
		for (int i = 0; i < C.M; i++)
			for (int j = 0; j < C.N; j++)
				for (int k = 0; k < A.N; k++)
					C.data[i][j] += (A.data[i][k] * B.data[k][j]);
		return C;
	}

	/* SOLVES A SYSTEM OF EQUATIONS */
	// return x = A^-1 b, assuming A is square and has full rank
	public Matrix solve(Matrix rhs) {
		if (M != N || rhs.M != N || rhs.N != 1) throw new RuntimeException("Illegal matrix dimensions.");

		// create copies of the data
		Matrix A = new Matrix(this);
		Matrix b = new Matrix(rhs);

		// Gaussian elimination with partial pivoting
		for (int i = 0; i < N; i++) {

			// find pivot row and swap
			int max = i;
			for (int j = i + 1; j < N; j++)
				if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i])) max = j;
			A.swap(i, max);
			b.swap(i, max);

			// singular
			if (A.data[i][i] == 0.0) throw new RuntimeException("Matrix is singular.");

			// pivot within b
			for (int j = i + 1; j < N; j++)
				b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];

			// pivot within A
			for (int j = i + 1; j < N; j++) {
				double m = A.data[j][i] / A.data[i][i];
				for (int k = i + 1; k < N; k++) {
					A.data[j][k] -= A.data[i][k] * m;
				}
				A.data[j][i] = 0.0;
			}
		}

		// back substitution
		Matrix x = new Matrix(N, 1);
		for (int j = N - 1; j >= 0; j--) {
			double t = 0.0;
			for (int k = j + 1; k < N; k++)
				t += A.data[j][k] * x.data[k][0];
			x.data[j][0] = (b.data[j][0] - t) / A.data[j][j];
		}
		return x;

	}

	private Matrix matrixOfMinors() {
		Matrix minors = new Matrix(M, N);

		for (int m = 0; m < M; m++) {
			for (int n = 0; n < N; n++) {
				Matrix subtracted = new Matrix(M - 1, N - 1);

				ArrayList<Integer> A = new ArrayList<Integer>();
				ArrayList<Integer> B = new ArrayList<Integer>();

				for (int a = 0; a < M; a++)
					A.add(a);
				A.remove(m);
				for (int b = 0; b < N; b++)
					B.add(b);
				B.remove(n);

				for (int a = 0; a < M - 1; a++)
					for (int b = 0; b < N - 1; b++)
						subtracted.data[a][b] = data[A.get(a)][B.get(b)];

				minors.data[m][n] = subtracted.determinant();
			}
		}

		return minors;
	}

	private Matrix matrixOfCofactors() {
		Matrix cofactors = new Matrix(this);

		for (int m = 0; m < M; m++)
			for (int n = 0; n < N; n++)
				cofactors.data[m][n] *= Math.pow(-1, m + n);

		return cofactors;
	}

	// find the determinant of a matrix
	public double determinant() {
		if (M == N && M == 1) return data[0][0];
		else if (M == N && M == 2) return data[0][0] * data[1][1] - data[1][0] * data[0][1];
		else {
			Matrix m = this.matrixOfMinors();
			Matrix c = this.matrixOfCofactors();

			double det = 0.0;
			for (int a = 0; a < N; a++)
				det += m.data[0][a] * c.data[0][a];
			return det;
		}
	}

	public void setTerm(int row, int col, double value) {
		data[row][col] = value;
	}

	public double getTerm(int row, int col) {
		return data[row][col];
	}

	public Matrix subMatrix(int m1, int n1, int m2, int n2) {
		if (m2 < m1) {
			int temp = m2;
			m2 = m1;
			m1 = temp;
		}
		if (n2 < n1) {
			int temp = n2;
			n2 = n1;
			n1 = temp;
		}
		if (m1 == m2) m2++;
		if (n1 == n2) n2++;

		Matrix sub = new Matrix(m2 - m1, n2 - n1);
		for (int m = m1; m < m2; m++)
			for (int n = n1; n < n2; n++)
				sub.setTerm(m - m1, n - n1, this.data[m][n]);

		return sub;
	}

	public String toString() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		for (int i = 0; i < M; i++) {
			ps.print((i == 0 ? "⎡" : (i == M - 1 ? "⎣" : "⎪")));

			for (int j = 0; j < N; j++)
				ps.printf("%9.4f ", data[i][j] + 0.000025);
			ps.println("\t" + (i == 0 ? "⎤" : (i == M - 1 ? "⎦" : "⎪")));
		}
		String content = new String(baos.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);
		return content;
	}

	private static Matrix parseString(String toParse) {
		return parseString(toParse, ";", ",");
	}

	private static Matrix parseString(String toParse, String lineDelim, String columnDelim) {
		if (toParse.charAt(0) == '[' && toParse.charAt(toParse.length() - 1) == ']')
			toParse = toParse.substring(1).substring(0, toParse.length() - 1);

		int M = toParse.split(lineDelim).length;
		int N = toParse.split(lineDelim)[0].split(columnDelim).length;

		double[][] data = new double[M][N];

		int m = 0, n = 0;

		for (String row : toParse.split(lineDelim)) {
			n = 0;
			for (String column : row.split(columnDelim))
				data[m][n++] = Double.parseDouble(column);
			m++;
		}

		return new Matrix(data);
	}

	public static void main(String[] args) {
		Matrix m = new Matrix("1,0,1;1,1,0;0,1,1");
		Matrix i = Matrix.identity(3);

		for (int a = 0; a < 25; a++) {
			i = i.times(m);

			System.out.println(i);
		}
	}
}
