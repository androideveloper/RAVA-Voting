package com.rava.voting.ui;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.rava.voting.R;
import com.rava.voting.RavaApplication;
import com.rava.voting.api.ElectionService;
import com.rava.voting.model.Answer;
import com.rava.voting.model.Election;
import com.rava.voting.model.ElectionState;
import com.rava.voting.model.Trustee;

public class ElectionDetailActivity extends Activity {

	public static final String TAG = "ElectionDetailActivity";
	public static final String KEY_ELECTION = "KEY_ELECTION";

	private Election mElection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_election_detail);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		Bundle bundle = getIntent().getExtras();
		int elId = bundle.getInt(KEY_ELECTION);

		RavaApplication app = (RavaApplication) getApplication();
		ElectionService electionService = app.getElectionService();
		electionService.getElection(elId, new Callback<Election>() {

			@Override
			public void success(Election election, Response response) {
				mElection = election;
				//
				Card card = new Card(ElectionDetailActivity.this);
				String info = "\n" + "Description:\t"
						+ mElection.getDescription() + "\n" + "State:\t"
						+ mElection.getState().getStateName() + "\n"
						+ "Public key:\t" + mElection.getPublicKey();
				card.setTitle(info);
				CardHeader header = new CardHeader(ElectionDetailActivity.this);
				header.setTitle(mElection.getName());
				card.addCardHeader(header);

				CardView cardView = (CardView) findViewById(R.id.card_title);
				cardView.setVisibility(View.VISIBLE);
				cardView.setCard(card);

				//
				Card cardTrustees = new Card(ElectionDetailActivity.this);
				String trustees = "";
				for (Trustee trustee : mElection.getTrustees()) {
					trustees += trustee.getEmail() + "\n";
				}
				cardTrustees.setTitle(trustees);
				CardHeader headerTrustees = new CardHeader(
						ElectionDetailActivity.this);
				headerTrustees.setTitle("Trustees");
				cardTrustees.addCardHeader(headerTrustees);

				CardView cardViewTrustees = (CardView) findViewById(R.id.card_trustees);
				cardViewTrustees.setVisibility(View.VISIBLE);
				cardViewTrustees.setCard(cardTrustees);

				//
				Card cardAnswers = new Card(ElectionDetailActivity.this);
				String answers = "";
				for (Answer answer : mElection.getAnswers()) {
					answers += answer.getAnswer();
					if (mElection.getState() == ElectionState.THREE) {
						answers += " - " + answer.getNumberOfVotes();
					}
					answers += "\n";
				}
				cardAnswers.setTitle(answers);
				CardHeader headerAnswers = new CardHeader(
						ElectionDetailActivity.this);
				headerAnswers.setTitle("Answers");
				cardAnswers.addCardHeader(headerAnswers);

				CardView cardViewAnswers = (CardView) findViewById(R.id.card_answers);
				cardViewAnswers.setVisibility(View.VISIBLE);
				cardViewAnswers.setCard(cardAnswers);
			}

			@Override
			public void failure(RetrofitError error) {
				Card card = new Card(ElectionDetailActivity.this);
				card.setTitle("Error loading election");

				CardView cardView = (CardView) findViewById(R.id.card_title);
				cardView.setVisibility(View.VISIBLE);
				cardView.setCard(card);
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
