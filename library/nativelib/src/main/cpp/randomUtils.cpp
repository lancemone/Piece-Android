//
// Created by motao on 2023/7/11.
//

#include "randomUtils.h"

/*
 * 生成随机字符串
 * 参数为字符串的长度
*/
std::string rand_str(const int len){
    std::string str;                 /*声明用来保存随机字符串的str*/
    char tmp;                   /*声明字符c，用来保存随机生成的字符*/
    int idx;                    /*用来循环的变量*/
    /*循环向字符串中添加随机生成的字符*/
    for(idx = 0;idx < len;idx ++)
    {
        int flag;
        flag = rand()%2;                     //随机使flag为1或0，为1就是大写，为0就是小写
        // 随机一个小于 36 的整数，0-9、A-Z 共 36 种字符
        /*rand()%36是取余，余数为0~25加上'a',就是字母a~z,详见asc码表*/
        tmp = random() % 36;
        if (tmp < 10) {			// 如果随机数小于 10，变换成一个阿拉伯数字的 ASCII
            tmp += '0';
        } else {				// 否则，变换成一个大写字母的 ASCII
            tmp -= 10;
            if (flag == 1){
                tmp += 'A';
            } else{
                tmp += 'a';
            }
        }
        str.push_back(tmp);       /*push_back()是string类尾插函数。这里插入随机字符c*/
    }
    return str;                 /*返回生成的随机字符串*/
}