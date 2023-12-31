package com.lhs.sts.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.lhs.sts.vo.MemberVO;

public interface MemberService {
	 public List<MemberVO> listMembers() throws DataAccessException;
	 public int addMember(MemberVO memberVO) throws DataAccessException;
	 public int removeMember(String id) throws DataAccessException;
	 public MemberVO login(MemberVO memberVO) throws Exception;
}
