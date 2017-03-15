package Crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class StudentsParser 
{
	public static List<Student> parse( File file, Crawler cr ) throws IOException
	{
		try( InputStream stream = new FileInputStream( file ) )
		{
			return parse( stream, cr );
		}
	}
	
	public static List<Student> parse( URL url , Crawler cr) throws IOException
	{
		try( InputStream stream = url.openStream() )
		{
			return parse( stream , cr);
		}
	}
	
	public static List<Student> parse( InputStream stream, Crawler cr ) throws IOException
	{
		try( InputStreamReader reader = new InputStreamReader( stream ) )
		{
			return parse( reader, cr );
		}
	}
	
	public static List<Student> parse( InputStreamReader reader, Crawler cr ) throws IOException
	{
		List<Student> result = new ArrayList<>();
		
		try( BufferedReader tmp = new BufferedReader( reader ) )
		{
			while( true )
			{
				String line = tmp.readLine();
				
				if( line == null )
					break;
				
				Student student = parseStudent( line );
				
				if( student == null )
					continue;
				
                                result.add(student);
                                cr.powiadom_dodano(student);
			}
			cr.i++;
		}
		
		return result;
	}

	private static Student parseStudent( String line )
	{
		String[] parts = line.split( ";" );
		
		if( parts.length == 4 )
		{
			for( String el : parts )
			{
				if( el.isEmpty() )
					return null;
			}

			try
			{
				Student student = new Student();
				
				student.setMark( Double.parseDouble( parts[ 0 ] ) );
				student.setFirstName( parts[ 1 ] );
				student.setLastName( parts[ 2 ] );
				student.setAge( Integer.parseInt( parts[ 3 ] ) );
				return student;
			}
			catch ( NumberFormatException e ) 
			{
				return null;
			}
		}
		
		return null;
	}
}

