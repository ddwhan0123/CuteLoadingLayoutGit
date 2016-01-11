# CuteLoadingLayoutGit
####Author:Ezreal Wang
####Blog:[http://blog.csdn.net/ddwhan0123](http://blog.csdn.net/ddwhan0123) 
演示效果：<br>
![Demo](https://raw.githubusercontent.com/ddwhan0123/CuteLoadingLayoutGit/master/CuteLoadingView/coco.gif "效果")
<br>
<br>
<br>
比较轻量级的自定义Loading界面，圆形的长条状的看腻了，不妨换一种体验
<br>
<br>
如何使用？<br>
```java
 public void setDefaultColor()//设置颜色
 public void setTextSize//设置字体大小
 public void setDefaultColor()//设置默认颜色（如果没有调用过setColor(),就无需调用此方法）
 public void setColor(int[] value)//设置边框颜色。如果数组长度不足6将抛出异常
 public void hideTextView()//隐藏六边形下方的文字部分
 public void stopTextJump()//关闭文字跳动动画
 public void startAnim()//开始动画，显示控件
 public void stopAnim()//结束动画，隐藏控件
```


