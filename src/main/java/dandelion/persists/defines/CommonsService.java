package dandelion.persists.defines;

import dandelion.wrapper.builder.ResultBuilder;
import dandelion.wrapper.defines.Paged;
import dandelion.wrapper.returns.GeneralService;
import dandelion.wrapper.returns.PagingResult;
import org.springframework.data.domain.Page;

import java.util.Collection;

/** @author Marcus */
public interface CommonsService extends GeneralService {

  /**
   * 返回分页对象
   *
   * @param source JPA分页对象
   * @param <E> 数据类型
   * @return 通用分页对象
   */
  default <E> PagingResult<E> paging(Page<E> source) {
    final var paged = buildPaging(source, source.getContent());
    return ResultBuilder.temporary(paged);
  }

  /**
   * 返回分页对象
   *
   * @param source JPA分页对象
   * @param record 实际数据集合
   * @param <T> 实际返回类型
   * @param <E> 实体数据类型
   * @return 通用分页对象
   */
  default <E, T> PagingResult<T> paging(Page<E> source, Collection<T> record) {
    final var paged = buildPaging(source, record);
    return ResultBuilder.temporary(paged);
  }

  /**
   * 构造公用分页对象
   *
   * @param source JPA分页对象
   * @param record 实际数据集合
   * @param <T> 实际返回类型
   * @param <E> 实体数据类型
   * @return 通用分页对象
   */
  private <E, T> Paged<T> buildPaging(final Page<E> source, final Collection<T> record) {
    final var paged = new Paged<T>();
    paged.setPage((long) source.getNumber());
    paged.setSize((long) source.getSize());
    final var offset = offset(source.getNumber(), source.getSize());
    paged.setSkip(offset);
    paged.setSums(source.getTotalElements());
    paged.setList(record);
    return paged;
  }

  /**
   * 计算偏移量
   *
   * @param page 当前页
   * @param size 页大小
   * @return 偏移量
   */
  private long offset(long page, long size) {
    final long x = page < 0 ? 0 : page;
    final long y = size < 0 ? 20 : size;
    return x * y;
  }
}
