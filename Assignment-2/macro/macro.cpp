#include <bits/stdc++.h>

using namespace std;


map<string, int> MNT;
map<string, string> ALA;
vector<string> MDT;  

vector<string> split (string str, char seperator){
	vector<string> ss;
    string s="";
    int i=0;
    while (i <= str.length()){
    	if (str[i] == seperator){
    		ss.push_back(s);
    		s = "";
    	} else {
    		s += str[i];
    	}
		i++;
    }
    ss.push_back(s);
    return ss;
}

void fparse(string s){
	ALA.clear();
	vector<string> args;
	s.erase(find(s.begin(), s.end(), '\0'), s.end());
	args = split(s, ' ');
	
	for (int i=0; i<args.size(); i++){
		if (args[i][args[i].length()-1] == ','){
			args[i] = args[i].substr(0, args[i].length()-1);
		}
		if (args[i][args[i].length()-1] == '='){
			args[i] = args[i].substr(0, args[i].length()-1);
		}
		args[i].erase(find(args[i].begin(), args[i].end(), '\0'), args[i].end());
		ALA.insert(pair<string, string>(args[i], "#"+to_string(i)));
	}
	return;	
}


string for_ind(string s){
	for (auto x: ALA){
		int pos = s.find(x.first);
		if (pos < s.length()){
			s.replace(pos , x.first.length(), x.second);
		}
	}
	return s;
}

void pass1(){
	ifstream input;
	ofstream inter, mnt, mdt;
	inter.open("intermediate.txt");
	mnt.open("mnt.txt");
	mdt.open("mdt.txt");
	input.open("input.txt");
	
	int mntc = 0;
	int mdtc = 0;
	
	string line;
	bool reading = 0;
	int	depth = 0;
	while (getline (input,line)){
		vector <string> ll;
		ll = split(line, '\t');

		if (ll.size() == 1){
			continue;
		}
		if (reading==true){
			
			line = for_ind(line);
			mdt << line << "\n";
			MDT.push_back(line);
			mdtc += 1;
			if (strcmp("MEND", ll[1].c_str()) == 0){
				reading = false;
			}
		} else if (reading == false){
			if (strcmp("MACRO", ll[1].c_str()) == 0){
			
				getline (input,line);
				ll = split(line, '\t');
				
				fparse(ll[2]);
				mdt << line << "\n";
				MDT.push_back(line);
				
				MNT.insert({ll[1], mdtc});
				mnt << ll[1] << "\t" << mdtc << "\n";
				
				mntc += 1;
				mdtc += 1;
				depth = 1;
				reading = true;
			} else {
				inter << line << "\n";
			}
		}
	}
	input.close();
	mnt.close();
	mdt.close();
	inter.close();
	return;
}


void fparse2(string s, ofstream &ala){
	ALA.clear();
	vector<string> args;
	args = split(s, '\t');
	ala << args[1] << "\n";
	
	s = args[2];
	s.erase(find(s.begin(), s.end(), '\0'), s.end());
	args = split(s, ' ');
	
	for (int i=0; i<args.size(); i++){
		if (args[i][args[i].length()-1] == ','){
			args[i] = args[i].substr(0, args[i].length()-1);
		}
		if (args[i][args[i].length()-1] == '='){
			args[i] = args[i].substr(0, args[i].length()-1);
		}
		args[i].erase(find(args[i].begin(), args[i].end(), '\0'), args[i].end());
		ala << args[i] << "\n";
		ALA.insert(pair<string, string>("#"+to_string(i), args[i]));
	}
	ala << "\n";
	return;	
}


void expand(string line, int mdtc, ofstream &output, ofstream &ala){
	fparse2(line, ala);
	map<string, string> temp = ALA;

	while (1){
		mdtc += 1;
		line = MDT[mdtc];
		vector <string> ll;
		ll = split(line, '\t');
		if (strcmp(ll[1].c_str(), "MEND") == 0 ){
			break;
		}
		line = for_ind(line);
		bool flag = false;
		for (auto x: MNT){
			if (strcmp(x.first.c_str(), ll[1].c_str()) == 0){
				flag = true;
				expand(line, x.second, output, ala);
				ALA = temp;
			}
		}
		
		if (not flag){
			output << line << "\n";
		}
		
		
	}
	return;
}

void pass2(){
	ifstream inter;
	ofstream output, ala;
	inter.open("intermediate.txt");
	output.open("output.txt");
	ala.open("ala.txt");

	string line;
	while (getline (inter,line)){
		vector <string> ll;
		ll = split(line, '\t');
		bool flag = false;
		for (auto x: MNT){
			if (strcmp(x.first.c_str(), ll[1].c_str()) == 0){
				expand(line, x.second, output, ala);
				flag = true;
			}
		}
		if (not flag){
			output << line <<"\n";
		}
	}
	
	inter.close();
	output.close();
	return;
}

int main(){
	pass1();
	pass2();
	return 0;
}

