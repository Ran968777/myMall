# 2020.9.15

- maven中 `<dependencyManagement>` 和 `<dependencies>`的区别。

  在我们项目顶层的POM文件中，我们会看到dependencyManagement元素。通过它元素来管理jar包的版本，让子项目中引用一个依赖而不用显示的列出版本号。Maven会沿着父子层次向上走，直到找到一个拥有dependencyManagement元素的项目，然后它就会使用在这个dependencyManagement元素中指定的版本号。

  相对于dependencyManagement，所有生命在dependencies里的依赖都会自动引入，并默认被所有的子项目继承。

  **区别:**

​	dependencies即使在子项目中不写该依赖项，那么子项目仍然会从父项目中继承该依赖项（全部继承）

​	dependencyManagement里只是声明依赖，并不实现引入，因此子项目需要显示的声明需要用的依赖。如果不在子项目中声明依赖，是不会从父项目中继承下来的；只有在子项目中写了该依赖项，并且没有指定具体版本，才会从父项目中继承该项，并且version和scope都读取自父pom;另外如果子项目中指定了版本号，那么会使用子项目中指定的jar版本。

# 2020.9.23

#### SpringSecurity的初认识

用户类需要继承UserDetail

用户service需要UserDetailService或者在自定义的UserService中调用SpringSecurity的UserDetailService的方法。

mall项目是自定义了登录的流程

vhr项目是给SpringSecurity自定义了LoginFilter（继承自）// todo



# 2020.9.28

#### 用户管理

- 登录时会保存下登录日志,记录下登录时间和IP