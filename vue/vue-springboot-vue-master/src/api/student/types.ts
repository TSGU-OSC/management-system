  
  /**
   * 学生查询对象类型
   */
  export interface StudentQuery extends PageQuery {
    //查询名称，这里就是查询用户名
    keywords?: string;
    
  }
  
  /**
   * 学生分页对象
   */
  export interface StudentPageVO {
    /**
     * 学生姓名
     */
    studentName?: string;

    //学生年龄
    studentAge?: number;

    //学生地址
    studentAddress?: string;

    //用户id
    id?: number;
    
  }
  
  /**
   * 用户表单类型
   */
  export interface StudentForm {
    
    /**
     * 用户ID
     */
    id?: number;
    
    //学生姓名
    studentName?: string;

    //学生年龄
    studentAge?: number;

    //学生地址
    studentAddress?: string;

  }
  