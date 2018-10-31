package data.privacy.query;

import java.util.HashMap;

//列联表  分析的基本问题是，判明所考察的各属性之间有无关联，即是否独立 （独立性检验）
public interface ContingencyTable {
	public HashMap<String, Double> getTable();
}
