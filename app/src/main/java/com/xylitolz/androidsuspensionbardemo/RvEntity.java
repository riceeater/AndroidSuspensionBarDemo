package com.xylitolz.androidsuspensionbardemo;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc RecyclerView适配器对应的实体类
 * @date 2018-05-15 13:51
 */
public class RvEntity {

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public RvEntity(int type) {

        this.type = type;
    }
}
