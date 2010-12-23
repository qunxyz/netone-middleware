package oe.bi.etl.bus.arithmetic;

import java.util.ArrayList;
import java.util.List;

import oe.bi.etl.bus.arithmetic.NDimension;


public class NDimensionImpl implements NDimension {

	public String[] nDimension(String[][] data, String linkSymbol) {
		int maxy = data.length;
		int linkSymbolLength = linkSymbol.length();
		List list = new ArrayList();
		if (maxy > 0)
			for (int a = 0; a < data[0].length; a++) {
				String tmp = linkSymbol + data[0][a];
				if (maxy == 1) {
					list.add(tmp.substring(linkSymbolLength));
				}
				if (maxy > 1)
					for (int b = 0; b < data[1].length; b++) {
						String tmp1 = tmp;
						tmp1 += linkSymbol + data[1][b];
						if (maxy == 2) {
							list.add(tmp1.substring(linkSymbolLength));
						}
						if (maxy > 2)
							for (int c = 0; c < data[2].length; c++) {
								String tmp2 = tmp1;
								tmp2 += linkSymbol + data[2][c];
								if (maxy == 3) {
									list.add(tmp2.substring(linkSymbolLength));
								}
								if (maxy > 3)
									for (int d = 0; d < data[3].length; d++) {
										String tmp3 = tmp2;
										tmp3 += linkSymbol + data[3][d];
										if (maxy == 4) {
											list
													.add(tmp3
															.substring(linkSymbolLength));
										}
										if (maxy > 4)
											for (int e = 0; e < data

											[4].length; e++) {
												String tmp4 = tmp3;
												tmp4 += linkSymbol +

												data[4][e];
												if (maxy == 5) {
													list
															.add

															(tmp4
																	.substring(linkSymbolLength));
												}
												if (maxy > 5)
													for (int f =

													0; f < data[5].length; f++) {

														String tmp5 = tmp4;
														tmp5

														+= linkSymbol

														+ data[5][f];
														if

														(maxy == 6) {

															list

																	.add(tmp5

																			.substring(linkSymbolLength));
														}
														if

														(maxy > 6)

															for (int g = 0; g < data[6].length; g++) {

																String tmp6 = tmp5;

																tmp6 += linkSymbol

																		+ data[6][g];

																if (maxy == 7) {

																	list

																			.add(tmp6

																					.substring(linkSymbolLength));

																}

																if (maxy > 7)

																	for (int h = 0; h < data[7].length; h++) {

																		String tmp7 = tmp6;

																		tmp7 += linkSymbol

																				+ data[7][h];

																		if (maxy == 8) {

																			list

																					.add(tmp7

																							.substring(linkSymbolLength));

																		}

																		if (maxy > 8)

																			for (int i = 0; i < data[8].length; i++) {

																				String tmp8 = tmp7;

																				tmp8 += linkSymbol

																						+ data[8][i];

																				if (maxy == 9) {

																					list

																							.add(tmp8

																									.substring(linkSymbolLength));

																				}

																				if (maxy > 9)

																					for (int j = 0; j < data[9].length; j++) {

																						String tmp9 = tmp8;

																						tmp9 += linkSymbol

																								+ data[9][j];

																						if (maxy == 10) {

																							list

																									.add(tmp9

																											.substring(linkSymbolLength));

																						}

																						if (maxy > 10)

																							for (int k = 0; k < data[10].length; k++) {

																								String tmp10 = tmp9;

																								tmp10 += data[10][k]

																										+ linkSymbol;

																								list

																										.add(tmp10);

																							}

																					}

																			}

																	}

															}
													}
											}
									}
							}
					}
			}
		return (String[]) list.toArray(new String[0]);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
