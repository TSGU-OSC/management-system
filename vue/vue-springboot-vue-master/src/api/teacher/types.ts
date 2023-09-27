  
  /**
   * Teacher查询对象类型
   */
  export interface TeacherQuery extends PageQuery {
    //查询名称，这里就是查询用户名
    keywords?: string;
    
  }
  
  /**
   * Teacher分页对象
   */
  export interface TeacherPageVO {
    /**
     * Teacher姓名
     */
    teacherName?: string;

    //teacher年龄
    teacherAge?: number;

    //teacher地址
    teacherAddress?: string;

    //用户id
    id?: number;
    
  }
  
  /**
   * 用户表单类型
   */
  export interface TeacherForm {
    
    /**
     * 用户ID
     */
    id?: number;
    
    //teacher姓名
    teacherName?: string;

    //teacher年龄
    teacherAge?: number;

    //teacher地址
    teacherAddress?: string;

  }
  