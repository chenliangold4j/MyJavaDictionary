package self.liang.reconsitution.example.seventh;

/**
 * 移除中间人
 *
 * 某个类做了过多的简单委托动作。
 *
 * hideDelegate有个缺点。
 *      如果受委托类有过多功能需要委托。那本服务类就完全变成了一个中间人。（如果示例中Department有很多功能，那么Person类就完全变成了DepartmentPlus。）
 *
 * 重构的意义在于：你不必说对不起。只要把问题修补好。
 *
 */
public class RemoveMiddleMan {
}
