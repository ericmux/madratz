package apitest;

import java.io.IOException;

import dnode.Callback;
import dnode.DNode;
import dnode.Server;
import dnode.netty.NettyServer;

public class Foo {
	
	private DNode dNode;
	private final Server server = new NettyServer(6060);
	
	public Foo() {
		Mooer mooer = new Mooer(7);
		dNode = new DNode(mooer);
		mooer.dNode = dNode;
		System.out.println("Listening");
		try {
			dNode.listen(server);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class Mooer{
		private final int moo;
		public DNode dNode;
		
		public Mooer(int moo){
			this.moo = moo;
		}
		
		public void moo(Callback cb) throws IOException {
			cb.call(moo);
			dNode.closeAllConnections();
			server.shutdown();
		}
		
		public void boo(Callback cb) throws IOException{
			cb.call(moo * 10);
			dNode.closeAllConnections();
			server.shutdown();
		}
	}

}
