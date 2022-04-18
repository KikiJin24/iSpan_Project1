package project1_copy;

public class SQLStatement {
	// 取得資料筆數(有幾列)
	public static String selectCount = "SELECT COUNT(*) CNT FROM ";

	public static String selectRow = "SELECT n.SEQ,n.SITE_CODE,d.SITE_NAME,"
			+ " n.FATHER_AGE,n.MOTHER_AGE,n.BIRTH_CNT,n.SEX,n.UPDATE_DATE"
			+ " FROM NEW_BORN_BABY n INNER JOIN DISTRICT d " + "ON (n.SITE_CODE = d.SITE_CODE)" + " WHERE SEQ = ";
	
	public static String selectAllDISTRICT = "SELECT * FROM DISTRICT";
	
	//Select的四個選項
	public static String selectJoinAll = "SELECT n.SEQ,n.SITE_CODE,d.SITE_NAME,"
			+ " n.FATHER_AGE,n.MOTHER_AGE,n.BIRTH_CNT,n.SEX,n.UPDATE_DATE"
			+ " FROM NEW_BORN_BABY n INNER JOIN DISTRICT d " + "ON (n.SITE_CODE = d.SITE_CODE)" + " ORDER BY SEQ;";

	public static String selectNonZero = "SELECT n.SEQ,n.SITE_CODE,d.SITE_NAME,"
			+ " n.FATHER_AGE,n.MOTHER_AGE,n.BIRTH_CNT,n.SEX,n.UPDATE_DATE"
			+ " FROM NEW_BORN_BABY n INNER JOIN DISTRICT d " + "ON (n.SITE_CODE = d.SITE_CODE)" + "WHERE BIRTH_CNT <> 0"
			+ " ORDER BY SEQ;";

	public static String selectBornCountSorted = "SELECT n.SEQ,n.SITE_CODE,d.SITE_NAME,"
			+ " n.FATHER_AGE,n.MOTHER_AGE,n.BIRTH_CNT,n.SEX,n.UPDATE_DATE"
			+ " FROM NEW_BORN_BABY n INNER JOIN DISTRICT d " + " ON (n.SITE_CODE = d.SITE_CODE)"
			+ " WHERE BIRTH_CNT <> 0" + " ORDER BY BIRTH_CNT DESC";

	public static String selectPremature = "SELECT n.SEQ,n.SITE_CODE,d.SITE_NAME,"
			+ " n.FATHER_AGE,n.MOTHER_AGE,n.BIRTH_CNT,n.SEX,n.UPDATE_DATE"
			+ " FROM NEW_BORN_BABY n INNER JOIN DISTRICT d " + " ON (n.SITE_CODE = d.SITE_CODE)"
			+ " WHERE BIRTH_CNT <> 0" + " AND FATHER_AGE IN('未滿20歲')" + " AND MOTHER_AGE IN ('未滿15歲','15～19歲')"
			+ " ORDER BY BIRTH_CNT DESC ";

//Create(Insert)
	public static String insertIntoDISTRICT = "INSERT INTO DISTRICT(SITE_CODE,SITE_NAME)"
			+ "VALUES(?,?);";
	
	public static String insertIntoNEW_BORN_BABY = "INSERT INTO NEW_BORN_BABY(SEQ,"
			+ "SITE_CODE,SEX,BIRTH_CNT,FATHER_AGE,MOTHER_AGE,UPDATE_DATE)"
			+ "VALUES(?,?,?,?,?,?,?);";
//Update
	public static String updateBirthCnt = "UPDATE NEW_BORN_BABY" + " SET BIRTH_CNT = ?, UPDATE_DATE = ?"
			+ " WHERE SEQ = ?";
//Delete
	public static String deleteARow = "DELETE FROM NEW_BORN_BABY WHERE SEQ = ?";

//Output用的(Join完成)
	public static String selectOutputAll = "SELECT n.SEQ,n.SITE_CODE,d.SITE_NAME,"
			+ " n.FATHER_AGE,n.MOTHER_AGE,n.BIRTH_CNT,n.SEX,n.UPDATE_DATE"
			+ " FROM NEW_BORN_BABY n INNER JOIN DISTRICT d " + "ON (n.SITE_CODE = d.SITE_CODE)";

	//寫不出來
//Stored Procedure
	public static String updateBirthCntStoredProcedure = "{call upd_birth_cnt(?,?,?)}";

	/*
	 * CREATE PROCEDURE upd_birth_cnt(
	 * 
	 * @birth_cnt	int,
	 * 
	 * @seq			int,
	 * 
	 * @update_date	Date 
	 * )AS
	 * BEGIN 
	 * UPDATE NEW_BORN_BABY 
	 * SET BIRTH_CNT=@birth_cnt,UPDATE_DATE=@update_date 
	 * WHERE SEQ=@seq END
	 * GO
	 */
	public static String selectRowBySeqStoredProcedure = "{call select_row_by_seq(?,?,?,?,?,?,?,?)}";

	/*
	 * CREATE PROCEDURE select_row_by_seq(
	 * 
	 * @seq				int,
	 * 
	 * @seqout			int			OUT,
	 * 
	 * @site_code		int			OUT,
	 * 
	 * @site_name	nvarchar(50)	OUT,
	 * 
	 * @f_age		nvarchar(100)	OUT,
	 * 
	 * @m_age		nvarchar(100)	OUT,
	 * 
	 * @birth_cnt		int			OUT,
	 * 
	 * @sex			nvarchar(5)		OUT 
	 * 
	 * )AS
	 * BEGIN
	 * SELECT @seqout=SEQ, @site_code=d.SITE_CODE, @site_name=d.SITE_NAME ,
	 * @f_age = FATHER_AGE, @m_age = n.MOTHER_AGE, @birth_cnt = n.BIRTH_CNT,
	 * @sex =n.SEX
	 * FROM NEW_BORN_BABY n INNER JOIN DISTRICT d
	 * 
	 * ON n.SITE_CODE = d.SITE_CODE
	 * 
	 * WHERE @seq= n.SEQ
	 * 
	 * END
	 * GO
	 */

}
