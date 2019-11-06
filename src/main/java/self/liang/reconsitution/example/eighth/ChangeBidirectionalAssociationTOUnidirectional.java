package self.liang.reconsitution.example.eighth;

/**
 *将双向关联改为单向。
 *
 * 双向关联维护起来复杂，容易出错。
 * 双向关联迫使两个类互相依赖，如果类位于不同包，这种就是包与包之间相依。过多的话造成紧耦合系统。
 *
 * 注重消除读取点，最好改为传入。。
 * 修改的缺点也由，，如果是自身还好，其他函数的调用可能获取不到关联的对象。
 *  所以这种关联性的修改容易引发bug。
 *  单元测试再复杂一些的修改中，极其重要。
 *
 *
 */
public class ChangeBidirectionalAssociationTOUnidirectional
{
}
