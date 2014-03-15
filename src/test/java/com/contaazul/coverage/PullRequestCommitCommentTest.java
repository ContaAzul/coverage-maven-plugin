package com.contaazul.coverage;

import static org.junit.Assert.*;

import org.junit.Test;

import com.contaazul.coverage.github.PullRequestCommitComment;

public class PullRequestCommitCommentTest {
	private static final String PATH = "not/a/real/path";
	private static final String SHA = "not a real sha";
	private PullRequestCommitComment comment;

	@Test
	public void testValidComment() throws Exception {
		comment = new PullRequestCommitComment( 1, 0, SHA, PATH, 1 );
		assertTrue( comment.isValid() );
		assertNotNull( comment.get() );
	}
	
	@Test
	public void testInvalidSha() throws Exception {
		comment = new PullRequestCommitComment( 1, 0, null, PATH, 1 );
		assertFalse( comment.isValid() );
		assertNotNull( comment.get() );
	}
	
	@Test
	public void testInvalidPosition() throws Exception {
		comment = new PullRequestCommitComment( 1, 0, SHA, PATH, -1 );
		assertFalse( comment.isValid() );
		assertNotNull( comment.get() );
	}
	
	@Test
	public void testInvalidPath() throws Exception {
		comment = new PullRequestCommitComment( 1, 0, SHA, null, -1 );
		assertFalse( comment.isValid() );
		assertNotNull( comment.get() );
	}
}
