package pe.com.cayetano.see.dataacademico.util;

import lombok.Generated;
import org.springframework.data.domain.Page;

import java.util.List;

public class CustomPage<T> {
  List<T> data;
  CustomPage<T>.CustomPageable pageable;

  public CustomPage() {
  }

  public CustomPage(Page<T> page) {
    this.data = page.getContent();
    this.pageable = new CustomPageable(page.getPageable().getPageNumber() + 1, page.getPageable().getPageSize(), page.getTotalElements());
  }

  @Generated
  public List<T> getData() {
    return this.data;
  }

  @Generated
  public CustomPage<T>.CustomPageable getPageable() {
    return this.pageable;
  }

  @Generated
  public void setData(final List<T> data) {
    this.data = data;
  }

  @Generated
  public void setPageable(final CustomPage<T>.CustomPageable pageable) {
    this.pageable = pageable;
  }

  @Generated
  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof CustomPage )) {
      return false;
    } else {
      CustomPage<?> other = (CustomPage)o;
      if (!other.canEqual(this)) {
        return false;
      } else {
        Object thisdata = this.getData();
        Object otherdata = other.getData();
        if (thisdata == null) {
          if (otherdata != null) {
            return false;
          }
        } else if (!thisdata.equals(otherdata)) {
          return false;
        }

        Object thispageable = this.getPageable();
        Object otherpageable = other.getPageable();
        if (thispageable == null) {
          if (otherpageable != null) {
            return false;
          }
        } else if (!thispageable.equals(otherpageable)) {
          return false;
        }

        return true;
      }
    }
  }

  @Generated
  protected boolean canEqual(final Object other) {
    return other instanceof CustomPage;
  }

  @Generated
  public int hashCode() {
    int result = 1;
    Object thisdata = this.getData();
    result = result * 59 + (thisdata == null ? 43 : thisdata.hashCode());
    Object pageables = this.getPageable();
    result = result * 59 + (pageables == null ? 43 : pageables.hashCode());
    return result;
  }

  @Generated
  public String toString() {
    var var10000 = this.getData();
    return "CustomPage(data=" + var10000 + ", pageable=" + this.getPageable() + ")";
  }

  class CustomPageable {
    int pageNumber;
    int pageSize;
    long totalElements;

    public CustomPageable() {
    }

    public CustomPageable(int pageNumber, int pageSize, long totalElements) {
      this.pageNumber = pageNumber;
      this.pageSize = pageSize;
      this.totalElements = totalElements;
    }

    @Generated
    public int getPageNumber() {
      return this.pageNumber;
    }

    @Generated
    public int getPageSize() {
      return this.pageSize;
    }

    @Generated
    public long getTotalElements() {
      return this.totalElements;
    }

    @Generated
    public void setPageNumber(final int pageNumber) {
      this.pageNumber = pageNumber;
    }

    @Generated
    public void setPageSize(final int pageSize) {
      this.pageSize = pageSize;
    }

    @Generated
    public void setTotalElements(final long totalElements) {
      this.totalElements = totalElements;
    }

    @Override
    @Generated
    public boolean equals(Object obj) {

      if (obj == null || this.getClass() != obj.getClass()) {
        return false;
      }

      if (obj == this) {
        return true;
      } else {
        CustomPage<?>.CustomPageable other = (CustomPageable)obj;
        if (!other.canEqual(this)) {
          return false;
        } else if (this.getPageNumber() != other.getPageNumber()) {
          return false;
        } else if (this.getPageSize() != other.getPageSize()) {
          return false;
        } else {
          return this.getTotalElements() == other.getTotalElements();
        }
      }
    }

    @Generated
    protected boolean canEqual(final Object other) {
      return other instanceof CustomPage;
    }

    @Generated
    public int hashCode() {
      int result = 1;
      result = result * 59 + this.getPageNumber();
      result = result * 59 + this.getPageSize();
      long totalElementos = this.getTotalElements();
      result = result * 59 + (int)(totalElementos >>> 32 ^ totalElementos);
      return result;
    }

    @Generated
    public String toString() {
      int var10000 = this.getPageNumber();
      return "CustomPage.CustomPageable(pageNumber=" + var10000 + ", pageSize=" + this.getPageSize() + ", totalElements=" + this.getTotalElements() + ")";
    }
  }
}
