package data.privacy.experiments.marginals;

import java.io.File;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Random;
import data.privacy.methods.*;

import data.privacy.data.*;

import data.privacy.query.*;

public class Bayes_marginals {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Random rng = new Random();
		
		//贝叶斯等级K
		int[] kb = {0, 4, 3, 2, 6, 1};
		//对应kb的下标
		int ds = Integer.parseInt(args[0]);												//dataset
		//a-way marginals
		int wl = Integer.parseInt(args[1]);												//degree of workload
		double epsilon = Double.parseDouble(args[2]);									//privacy budget
		String sourceData = args[3];   //数据集
		String domainData = args[4];//属性集
		String report = args[5];//输出
		
		Data gData = new Data(sourceData, new Domain(domainData));		//load data (in general domain)
		Data bData = gData.binarization();												//binarization
		//bData.loadCache("bData"+ds+"_"+(kb[ds]+1)+"ways.cache");						//cache file contains ALL k-way marginals of the BINARY DATA (makes computation faster)
		int rep = 1;
				
		HashSet<Marginal> mrgs = QueryGenerator.kwayMrg(gData, wl);							//generate workload
		System.out.println(mrgs);
		PrintStream outFile = new PrintStream(new File(report));

		for (int i = 0; i < rep; i++){
			System.out.println(	);
			Bayesian	bayes = new Bayesian(rng, bData, epsilon, 0.5, 4.0, kb[ds]);	//PrivBayes
			Data		gSyn = bayes.release().generalization();						//release result of PrivBayes (BINARY), then convert to general domain
			
			for (Marginal mrg : mrgs) {
				gData.mError(gSyn, mrg, outFile);										//total variation distance of each marginal
			}	
			outFile.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		}

		outFile.close();			
	}
}
